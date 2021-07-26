package info.exult;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class ExultLauncherActivity extends Activity {
    //TODO: synchronize?
    private HashMap<String, Integer> m_gamenameToRequestCode = new HashMap<String,Integer>();
    private Integer m_audioPackRequestCode = null;
    private HashMap<String, Integer> m_modnameToRequestCode = new HashMap<String,Integer>();
    private HashMap<Integer,ExultContent> m_requestCodeToContent = new HashMap<Integer,ExultContent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setMovementMethod(LinkMovementMethod.getInstance());

        ViewGroup gamesLayout = (ViewGroup) findViewById(R.id.gamesLayout);
        for (int i = 0; i < gamesLayout.getChildCount(); ++i) {
            View viewInGamesLayout = gamesLayout.getChildAt(i);
            String gamename = (String) viewInGamesLayout.getTag(R.id.gamename);
            if (null == gamename) {
                continue;
            }
            String crc32String = (String) viewInGamesLayout.getTag(R.id.crc32);
            Long crc32 = null;
            if (crc32String != null) {
                crc32 = Long.decode(crc32String);
            }
            ExultContent gameContent = new ExultGameContent(gamename, getApplicationContext(), crc32);
            int requestCode = m_requestCodeToContent.size() + 1;
            m_gamenameToRequestCode.put(gamename, requestCode);
            m_requestCodeToContent.put(requestCode, gameContent);
            if (!gameContent.isInstalled()) {
                continue;
            }
            CheckBox gameCheckBox = (CheckBox) viewInGamesLayout;
            gameCheckBox.setChecked(true);
        }

        CheckBox audioPackCheckBox = (CheckBox) findViewById(R.id.audioPackCheckBox);
        ExultContent audioPackContent = new ExultAudioDataPackContent("allInOne", getApplicationContext());
        m_audioPackRequestCode = m_requestCodeToContent.size() + 1;
        m_requestCodeToContent.put(m_audioPackRequestCode, audioPackContent);
        if (audioPackContent.isInstalled()) {
            audioPackCheckBox.setChecked(true);
        }

        for (int i = 0; i < gamesLayout.getChildCount(); ++i) {
            View viewInGamesLayout = gamesLayout.getChildAt(i);
            String modname = (String) viewInGamesLayout.getTag(R.id.modname);
            if (null == modname) {
                continue;
            }
            String modgame = (String) viewInGamesLayout.getTag(R.id.modgame);
            if (null == modgame) {
                continue;
            }
            Log.d("ExultLauncherActivity", "Found " + modgame + "." + modname);
            ExultContent modContent = new ExultModContent(modgame, modname, getApplicationContext());
            int requestCode = m_requestCodeToContent.size() + 1;
            m_modnameToRequestCode.put(modgame + "." + modname, requestCode);
            m_requestCodeToContent.put(requestCode, modContent);
            if (!modContent.isInstalled()) {
                continue;
            }
            CheckBox modCheckBox = (CheckBox) viewInGamesLayout;
            modCheckBox.setChecked(true);
        }
    }

    void installGameContent(Uri uri, ExultContent gameContent) {
        try {
            if (gameContent.isInstalled()) {
                gameContent.uninstall();
            }
            boolean result = gameContent.install(uri);
            //TODO: uncheck and display dialog on failure
            return;
        } catch (Exception e) {
            Log.d("ExultLauncherActivity",
                  "exception installing " + uri + ": " + e.toString());
        } 
    }

    private void onCheckboxClicked(View view, int requestCode) {
        CheckBox checkBox  = (CheckBox) view;
        boolean checked = checkBox.isChecked();

        if (checked) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, requestCode);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    public void onGameCheckboxClicked(View view) {
        String gamename = (String) view.getTag(R.id.gamename);
        Integer requestCode = m_gamenameToRequestCode.get(gamename);
        if (null == requestCode) {
            return;
        }
        onCheckboxClicked(view, requestCode);
    }

    public void onAudioPackCheckboxClicked(View view) {
        onCheckboxClicked(view, m_audioPackRequestCode);
    }

    public void onModCheckboxClicked(View view) {
        Log.d("ExultLauncherActivity", "onModCheckboxClicked()");
        String modname = (String) view.getTag(R.id.modname);
        String modgame = (String) view.getTag(R.id.modgame);
        Log.d("ExultLauncherActivity", modgame + "." + modname + " checkbox clicked");
        Integer requestCode = m_modnameToRequestCode.get(modgame + "." + modname);
        if (null == requestCode) {
            return;
        }
        onCheckboxClicked(view, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (resultCode != Activity.RESULT_OK || null == resultData) {
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

    public void launchExult(View view) {
        Intent launchExultIntent = new Intent(this, ExultActivity.class);
        startActivity(launchExultIntent);
    }
}
