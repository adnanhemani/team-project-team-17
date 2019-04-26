package com.example.cs160_sp18.prog3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

// Displays a list of comments for a particular landmark.
public class CommentFeedActivity extends AppCompatActivity {

    private static final String TAG = CommentFeedActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Comment> mComments = new ArrayList<Comment>();
    private ArrayList<String> mCommentKeys = new ArrayList<>();
    private static String username;
    private String landmarkName;
    private boolean sort_chron = true;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    // UI elements
    EditText commentInputBox;
    RelativeLayout layout;
    Button sendButton;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_feed);
        final Button sort = findViewById(R.id.sorting);
        Intent goToSecondActivityIntent = getIntent();
        Bundle intentExtras = goToSecondActivityIntent.getExtras();

        landmarkName = "";

        if(intentExtras!=null) {
            landmarkName =(String)intentExtras.get("chatroom");
            username = (String)intentExtras.get("username");
        }


        // sets the app bar's title
        setTitle(landmarkName + ": Posts");

        // hook up UI elements
        layout = (RelativeLayout) findViewById(R.id.comment_layout);
        commentInputBox = (EditText) layout.findViewById(R.id.comment_input_edit_text);
        sendButton = (Button) layout.findViewById(R.id.send_button);

        mToolbar = (Toolbar) findViewById(R.id.bear_toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an onclick for the send button
        setOnClickForSendButton();


        // get comments from Firebase
        getCommentsFromDB(landmarkName);

        // use the comments in mComments to create an adapter. This will populate mRecyclerView
        // with a custom cell (with comment_cell_layout) for each comment in mComments
        setAdapterAndUpdateData();

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort_chron = !sort_chron;
                if (!sort_chron) {
                    getCommentsFromDB(landmarkName);
                    sort.setText("Sort By Time");

                } else {
                    getCommentsFromDB(landmarkName);
                    sort.setText("Sort By Upvotes");
                }
                setAdapterAndUpdateData();
            }
        });
    }

//    // TODO: delete me
//    private void makeTestComments() {
//        String randomString = "hello world hello world ";
//        Comment newComment = new Comment(randomString, "test_user1", new Date());
//        Comment hourAgoComment = new Comment(randomString + randomString, "test_user2", new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
//        Comment overHourComment = new Comment(randomString, "test_user3", new Date(System.currentTimeMillis() - (2 * 60 * 60 * 1000)));
//        Comment dayAgoComment = new Comment(randomString, "test_user4", new Date(System.currentTimeMillis() - (25 * 60 * 60 * 1000)));
//        Comment daysAgoComment = new Comment(randomString + randomString + randomString, "test_user5", new Date(System.currentTimeMillis() - (48 * 60 * 60 * 1000)));
//        mComments.add(newComment);mComments.add(hourAgoComment); mComments.add(overHourComment);mComments.add(dayAgoComment); mComments.add(daysAgoComment);
//    }

    private void getCommentsFromDB(String landmarkName) {
        Query myTopPostsQueryTime = mDatabase.child(landmarkName).orderByChild("date");
        Query myTopPostsQueryUpvote = mDatabase.child(landmarkName).orderByChild("upvotes");

        myTopPostsQueryTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (sort_chron) {
                    mComments.clear();
                    mCommentKeys.clear();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Comment comment = postSnapshot.getValue(Comment.class);
                        mComments.add(comment);
                        mCommentKeys.add(postSnapshot.getKey());
                    }
                    setAdapterAndUpdateData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Comment failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        myTopPostsQueryUpvote.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!sort_chron) {
                    mComments.clear();
                    mCommentKeys.clear();
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Comment comment = postSnapshot.getValue(Comment.class);
                        mComments.add(0, comment);
                        mCommentKeys.add(0, postSnapshot.getKey());
                    }
                    setAdapterAndUpdateData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Comment failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void setOnClickForSendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentInputBox.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    // don't do anything if nothing was added
                    commentInputBox.requestFocus();
                } else {
                    // clear edit text, post comment
                    commentInputBox.setText("");
                    postNewComment(comment);
                }
            }
        });
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new CommentAdapter(this, mComments, mCommentKeys, landmarkName);
        mRecyclerView.setAdapter(mAdapter);

        // scroll to the last comment
        if (mComments.size() == 0) {
            mRecyclerView.smoothScrollToPosition(0);
        } else {
            mRecyclerView.smoothScrollToPosition(mComments.size() - 1);
        }

    }

    private void postNewComment(String commentText) {
        Comment newComment = new Comment(commentText, username, new Date());
        mComments.add(newComment);

        // Send comment to Firebase
        String commentId = mDatabase.push().getKey();
        mCommentKeys.add(commentId);
        mDatabase.child(landmarkName).child(commentId).setValue(newComment);

        setAdapterAndUpdateData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
