package info.exult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.widget.CheckBox;
import android.content.Intent;
import android.app.AlertDialog;
import android.net.Uri;

import android.webkit.WebView;
import android.graphics.Color;

import android.util.Log;

import java.util.HashMap;

public abstract class ContentInstallerFragment extends Fragment implements View.OnClickListener {

  private HashMap<String, Integer> m_nameToRequestCode = new HashMap<String,Integer>();
  private HashMap<Integer,ExultContent> m_requestCodeToContent = new HashMap<Integer,ExultContent>();
  final private int m_layout;
  final private int m_text;

  ContentInstallerFragment(int layout, int text) {
      m_layout = layout;
      m_text = text;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

      View view = inflater.inflate(m_layout, container, false);

      WebView webView = (WebView) view.findViewById(R.id.contentWebView);
      if (webView != null) {
          webView.loadData(getString(m_text), "text/html", "utf-8");
          webView.setBackgroundColor(Color.TRANSPARENT);
          webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
      }

      ViewGroup contentLayout = (ViewGroup) view.findViewById(R.id.contentLayout);
      ExultLauncherActivity activity = (ExultLauncherActivity) getActivity();
      for (int i = 0; i < contentLayout.getChildCount(); ++i) {
          View viewInContentLayout = contentLayout.getChildAt(i);
          String name = (String) viewInContentLayout.getTag(R.id.name);
          if (null == name) {
              continue;
          }
          ExultContent content = buildContentFromView(name, viewInContentLayout);
          if (null == content) {
              continue;
          }
          int requestCode = m_requestCodeToContent.size() + 1;
          m_nameToRequestCode.put(name, requestCode);
          m_requestCodeToContent.put(requestCode, content);
          viewInContentLayout.setOnClickListener(this);
          if (!content.isInstalled()) {
              continue;
          }
          CheckBox contentCheckBox = (CheckBox) viewInContentLayout;
          contentCheckBox.setChecked(true);
      }

      return view;
  }

  protected abstract ExultContent buildContentFromView(String name, View view);

  private void onCheckboxClicked(View view, int requestCode) {
        CheckBox checkBox  = (CheckBox) view;
        boolean checked = checkBox.isChecked();

        if (checked) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, requestCode);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Uninstall " + checkBox.getText() + "?");
            builder.setMessage("This will uninstall " + checkBox.getText() + ".  Are you sure?");
            builder.setPositiveButton("Yes",
                                      (dialog, which) -> {
                                          ExultContent content = m_requestCodeToContent.get(requestCode);
                                          if (null == content) {
                                              return;
                                          }
                                          if (content.isInstalled()) {
                                              try {
                                                  content.uninstall();
                                              } catch (Exception e) {
                                                  Log.d("ExultLauncherActivity", "exception deleting " +
                                                        content.getName() + ": " +
                                                        e.toString());
                                              }
                                          }
                                      });
            builder.setNegativeButton("No",
                                      (dialog, which) -> {
                                          checkBox.setChecked(true);
                                      });
            AlertDialog alert = builder.create();
            alert.show();
        }
  }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (null == resultData) {
            return;
        }

        ExultContent content = m_requestCodeToContent.get(requestCode);
        if (null == content) {
            return;
        }

        // The result data contains a URI for the document or directory that
        // the user selected.
        Uri uri = null;
        uri = resultData.getData();
        // Perform operations on the document using its URI.
        try {
            if (content.isInstalled()) {
                content.uninstall();
            }
            boolean result = content.install(uri);
            //TODO: uncheck and display dialog on failure
        } catch (Exception e) {
            Log.d("ExultLauncherActivity", "exception opening content: " + e.toString());
        }
  }

  @Override
  public void onClick(View view) {
        String name = (String) view.getTag(R.id.name);
        Integer requestCode = m_nameToRequestCode.get(name);
        Log.d("GamesFragment", "onClick(), name=" + name +  ", requestCode=" + requestCode);
        if (null == requestCode) {
            return;
        }
        onCheckboxClicked(view, requestCode);
  }
}
