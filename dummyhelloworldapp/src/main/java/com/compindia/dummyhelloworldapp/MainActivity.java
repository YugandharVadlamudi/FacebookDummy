package com.compindia.dummyhelloworldapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.optimizely.CodeBlocks.CodeBranch;
import com.optimizely.CodeBlocks.DefaultCodeBranch;
import com.optimizely.CodeBlocks.OptimizelyCodeBlock;
import com.optimizely.Optimizely;

public class MainActivity extends AppCompatActivity {
private static OptimizelyCodeBlock optimizelyCodeBlock;
    private LinearLayout llDummyHellworldApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Optimizely.startOptimizelyWithAPIToken("AANT9csB8HHPQEuhRBOMOJ-Pt3wqooRm~6997470376", getApplication());
        setContentView(R.layout.activity_main);
        optimizelyCodeBlock = Optimizely.codeBlock("NewFeature").withBranchNames("newFeature");
        llDummyHellworldApp = (LinearLayout) findViewById(R.id.ll_dummyhellworldapp);

    }

    private void newFeature() {
        optimizelyCodeBlock.execute(new DefaultCodeBranch() {
            @Override
            public void execute() {

            }
        }, new CodeBranch() {
            @Override
            public void execute() {
                setUpNewFeatures();           
            }
        });
    }

    private void setUpNewFeatures() {
        Button button = new Button(getApplicationContext());
        button.setText("Hellow");
        llDummyHellworldApp.addView(button);
    }
}
