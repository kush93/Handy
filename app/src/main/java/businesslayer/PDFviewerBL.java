package businesslayer;

/**
 * Created by rahul on 3/13/2016.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.kushal.rihabhbhandari.R;

import java.io.File;
import java.io.IOException;

public class PDFviewerBL extends Fragment implements  View.OnClickListener {

    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";
    private ParcelFileDescriptor fileDescriptor;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ImageView imageView;
    private Button buttonPrevious;
    private Button buttonNext;

    public PDFviewerBL(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pdf_renderer_basic, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Retain view references.
        imageView = (ImageView) view.findViewById(R.id.image);
        buttonPrevious = (Button) view.findViewById(R.id.previous);
        buttonNext = (Button) view.findViewById(R.id.next);
        // Bind events.
        buttonPrevious.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        // Show the first page by default.
        int index = 0;
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState) {
            index = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
        }
        showPage(index);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            openRenderer(activity);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }
    @Override
    public void onDetach() {
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDetach();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != currentPage) {
            outState.putInt(STATE_CURRENT_PAGE_INDEX, currentPage.getIndex());
        }
    }
    private void openRenderer(Context context) throws IOException {
        // In this sample, we read a PDF from the assets directory.
//        mFileDescriptor=ParcelFileDescriptor.open(new File(context.getEx), ParcelFileDescriptor.MODE_READ_ONLY)
        fileDescriptor = context.getAssets().openFd("Rahul.pdf").getParcelFileDescriptor();
        // This is the PdfRenderer we use to render the PDF.
        pdfRenderer = new PdfRenderer(fileDescriptor);
    }

    private void closeRenderer() throws IOException {
        if (null != currentPage) {
            currentPage.close();
        }
        pdfRenderer.close();
        fileDescriptor.close();
    }
    private void showPage(int index) {
        if (pdfRenderer.getPageCount() <= index) {
            return;
        }
        if (null != currentPage) {
            currentPage.close();
        }
        currentPage = pdfRenderer.openPage(index);
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        imageView.setImageBitmap(bitmap);
        updateUi();
    }
    private void updateUi() {
        int index = currentPage.getIndex();
        int pageCount = pdfRenderer.getPageCount();
        buttonPrevious.setEnabled(0 != index);
        buttonNext.setEnabled(index + 1 < pageCount);
        getActivity().setTitle(getString(R.string.app_name_with_index, index + 1, pageCount));
    }
    public int getPageCount() {
        return pdfRenderer.getPageCount();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous: {
                // Move to the previous page
                showPage(currentPage.getIndex() - 1);
                break;
            }
            case R.id.next: {
                // Move to the next page
                showPage(currentPage.getIndex() + 1);
                break;
            }
        }
    }

}

}
