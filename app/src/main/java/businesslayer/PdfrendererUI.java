
package businesslayer;

import android.app.Activity;
import android.os.Bundle;


import com.example.kushal.rihabhbhandari.R;

public class PdfrendererUI extends Activity{
    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_viewer);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PdfRendererBasicFragment(),
                            FRAGMENT_PDF_RENDERER_BASIC)
                    .commit();

        }
    }
}