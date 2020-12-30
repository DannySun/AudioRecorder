package com.dreamfish.audiorecord;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.core.content.FileProvider;

import com.dreamfish.record.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HXL on 16/8/11.
 */
public class ListActivity extends Activity {
    ListView listView;
    List<File> list = new ArrayList<>();
    FileListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listView);
        if ("pcm".equals(getIntent().getStringExtra("type"))) {
            list = FileUtil.getPcmFiles();
        } else {
            list = FileUtil.getWavFiles();
        }

        adapter = new FileListAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
//                Uri uri = Uri.fromFile(list.get(position));
                Uri contentUri = FileProvider.getUriForFile(ListActivity.this, getApplicationContext().getPackageName() + ".FileProvider", list.get(position));
                intent.setDataAndType(contentUri,"audio/*");
//                startActivity(intent);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
