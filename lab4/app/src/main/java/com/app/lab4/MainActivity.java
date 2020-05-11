package com.app.lab4;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import java.util.concurrent.LinkedBlockingQueue;
        import java.util.concurrent.ThreadPoolExecutor;
        import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    private Handler handler;
    TextView tvStatus;
    int curCount = 0;
    ProgressBar progressBar;
    String url1 = "http://cdn3.pcadvisor.co.uk/cmsdata/features/3420161/Android_800_thumb800.jpg";
    String url2 = "http://cdn2.ubergizmo.com/wp-content/uploads/2015/05/android-logo.jpg";
    float totalCount = 50F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                tvStatus = findViewById(R.id.tvDownloadCount);
                progressBar = findViewById(R.id.progressBar);

                int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
                ThreadPoolExecutor executor = new ThreadPoolExecutor(
                        NUMBER_OF_CORES * 2,
                        NUMBER_OF_CORES * 2,
                        60L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>()
                );

                for (int i = 0; i < totalCount; i++) {
                    String imageUrl = (i % 2 == 0) ? url1 : url2;
                    executor.execute(new LongThread(i, imageUrl, new Handler(this)));
                }

                tvStatus.setText("Starting Download...");
            }

            @Override
            public boolean handleMessage(Message msg) {
                curCount++;
                float per = (curCount / totalCount) * 100;
                progressBar.setProgress((int) per);
                if (per < 100)
                    tvStatus.setText("Downloaded [" + curCount + "/" + (int)totalCount + "]");
                else
                    tvStatus.setText("image 1 downloded");
                return true;
            }
        }

