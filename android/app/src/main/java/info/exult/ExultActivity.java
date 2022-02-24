package info.exult;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Gravity;
import android.widget.RelativeLayout;

import org.libsdl.app.SDLActivity;

public class ExultActivity extends SDLActivity
{
    private static ExultActivity m_instance;
    public static String consoleLog;

    public void writeToConsole(String message) {
        if (message != null) {
            consoleLog += message + "\n";
        }
    }

    public static ExultActivity instance(){
        return m_instance;
    }

    public void showGameControls(String dpadLocation) {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout.LayoutParams dpadLayoutParams = (RelativeLayout.LayoutParams) m_dpadImageView.getLayoutParams();
                    dpadLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    dpadLayoutParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);

                    switch (dpadLocation) {
                    case "left":
                        dpadLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        dpadLayoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        break;
                    case "right":
                        dpadLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        dpadLayoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        break;
                    default:
                        m_dpadImageView.setVisibility(View.GONE);
                        return;
                    }
                    m_dpadImageView.setLayoutParams(dpadLayoutParams);
                    m_dpadImageView.setVisibility(View.VISIBLE);
                }
            });        
    }

    public void hideGameControls() {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    m_dpadImageView.setVisibility(View.GONE);
                }
            });        
    }

    public void showButtonControls(String dpadLocation) {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RelativeLayout.LayoutParams escLayoutParams = (RelativeLayout.LayoutParams) m_escTextView.getLayoutParams();
                    escLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    escLayoutParams.removeRule(RelativeLayout.ALIGN_PARENT_TOP);

                    switch (dpadLocation) {
                    case "left":
                        escLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        escLayoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        break;
                    case "right":
                        escLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        escLayoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        break;
                    default:
                        m_escTextView.setVisibility(View.GONE);
                        return;
                    }
                    m_escTextView.setLayoutParams(escLayoutParams);
                    m_escTextView.setVisibility(View.VISIBLE);
                }
            });
    }

    public void hideButtonControls() {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    m_escTextView.setVisibility(View.GONE);
                }
            });
    }

    private TextView m_escTextView;

    private ImageView m_dpadImageView;
    private float m_dpadImageViewActionDownX;
    private float m_dpadImageViewActionDownY;

    public native void setVirtualJoystick(float x, float y);
    public native void sendEscapeKeypress();
    public native void setName(String name);

    public void promptForName(String name) {
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Context context = getContext();
                    AlertDialog.Builder nameBuilder = new AlertDialog.Builder(context);
                    nameBuilder.setTitle("Name");
                    EditText nameEditText = new EditText(context);
                    nameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    nameBuilder.setView(nameEditText);
                    nameBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setName(nameEditText.getText().toString());
                            }
                        });
                    nameBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                    nameBuilder.show();
                }
            });        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_instance = this;

        m_escTextView = new TextView(this);
        m_escTextView.setBackground(getResources().getDrawable(R.drawable.btn));
        m_escTextView.setPadding(20, 20, 20, 20);
        m_escTextView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        m_escTextView.setGravity(Gravity.CENTER);
        m_escTextView.setText("ESC");

        m_escTextView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    boolean inside =
                        !(event.getX() < 0 || event.getY() < 0
                          || event.getX() > v.getMeasuredWidth()
                          || event.getY() > v.getMeasuredHeight());
                    switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        m_escTextView.setBackground(getResources().getDrawable(R.drawable.btnpressed));
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        m_escTextView.setBackground(getResources().getDrawable(inside ? R.drawable.btnpressed : R.drawable.btn));
                        return true;
                    case MotionEvent.ACTION_UP:
                        m_escTextView.setBackground(getResources().getDrawable(R.drawable.btn));
                        if (inside) {
                            sendEscapeKeypress();
                        }
                        return true;
                    }
                    return false;
                }
            });

        m_dpadImageView = new ImageView(this);
        m_dpadImageView.setImageResource(R.drawable.dpad_center);
        m_dpadImageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        m_dpadImageViewActionDownX = event.getRawX();
                        m_dpadImageViewActionDownY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float deltaX = event.getRawX() - m_dpadImageViewActionDownX;
                        float deltaY = event.getRawY() - m_dpadImageViewActionDownY;

                        setVirtualJoystick(deltaX / (m_dpadImageView.getWidth() / 2), deltaY / (m_dpadImageView.getHeight() / 2));

                        double radius = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                        if (radius < m_dpadImageView.getWidth() / 8) {
                            m_dpadImageView.setImageResource(R.drawable.dpad_center);
                            return true;
                        }
                        int[] directionDrawables = {
                            R.drawable.dpad_east,
                            R.drawable.dpad_southeast,
                            R.drawable.dpad_south,
                            R.drawable.dpad_southwest,
                            R.drawable.dpad_west,
                            R.drawable.dpad_northwest,
                            R.drawable.dpad_north,
                            R.drawable.dpad_northeast
                        };
                        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)) + 360;
                        int directionCount = directionDrawables.length;
                        double directionWidth = 360.0 / directionCount;
                        int direction = (int) Math.round(angle / directionWidth) % directionCount;
                        m_dpadImageView.setImageResource(directionDrawables[direction]);
                        return true;
                    case MotionEvent.ACTION_UP:
                        setVirtualJoystick(0, 0);
                        m_dpadImageView.setImageResource(R.drawable.dpad_center);
                        return true;
                    }
                    return false;
                }
            });
        hideGameControls();
        hideButtonControls();
        mLayout.addView(m_escTextView);
        mLayout.addView(m_dpadImageView);
    }

    /**
     * This method returns the name of the application entry point
     * It can be overridden by derived classes.
     */
    protected String getMainFunction() {
        return "ExultAndroid_main";
    }

    /**
     * This method is called by SDL before loading the native shared libraries.
     * It can be overridden to provide names of shared libraries to be loaded.
     * The default implementation returns the defaults. It never returns null.
     * An array returned by a new implementation must at least contain "SDL2".
     * Also keep in mind that the order the libraries are loaded may matter.
     *
     * @return names of shared libraries to be loaded (e.g. "SDL2", "main").
     */
    protected String[] getLibraries() {
        return new String[] {
            "SDL2",
            "exult-android-wrapper"
        };
    }
}
