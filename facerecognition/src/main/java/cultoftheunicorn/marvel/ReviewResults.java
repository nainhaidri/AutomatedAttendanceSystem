package cultoftheunicorn.marvel;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// uncomment when you enable firebase
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import org.opencv.cultoftheunicorn.marvel.R;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Attendees {

    public String names;
    public String date;

    public Attendees() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Attendees(String names, String date) {
        this.names  = names;
        this.date = date;
    }

}

public class ReviewResults extends AppCompatActivity implements ReviewListAdapter.ClickListener {

    private List<String> commitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_results);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Button commitButton = (Button) findViewById(R.id.button);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Review and Mark");
        }

        List<String> reviewList = Arrays.asList(getIntent().getStringArrayExtra("list"));

        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this, reviewList);
        reviewListAdapter.setClickListener(this);
        recyclerView.setAdapter(reviewListAdapter);

        //Setting LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        /*//For adding dividers in the list
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);*/


    }

    @Override
    public void onItemClick(String name) {
        if(commitList.contains(name))
            commitList.remove(name);
        else
            commitList.add(name);
    }

    public void commit() {

        if(commitList.size() != 0) {
           File f = new File(Environment.getExternalStorageDirectory()+"/facerecogOCV/attendance.txt");
            PrintWriter pw = null;
           try{
               pw = new PrintWriter(f);
           }
           catch(Exception e){

           }
           for (int i = 0 ; i < commitList.size(); i++){
               pw.write(commitList.get(i)+"\n");
           }
           pw.close();
            Toast.makeText(getApplicationContext(),"Attendance Marked",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please select at least one student", Toast.LENGTH_SHORT).show();
        }
    }
}
