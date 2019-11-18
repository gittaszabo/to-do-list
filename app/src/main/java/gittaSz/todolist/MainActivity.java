package gittaSz.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import gittaSz.todolist.adapter.ToDoListAdapter;
import gittaSz.todolist.model.ToDoItem;
import gittaSz.todolist.model.ToDoItemDAO;

public class MainActivity extends AppCompatActivity {

    private static final int REQC_NEW = 1;
    private static final int REQC_EDIT = 2;
    private boolean filter;
    private List<ToDoItem> tasks;
    private ToDoListAdapter adapter;
    private ToDoItemDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ToDoItemActivity.class);
                startActivityForResult(intent, REQC_NEW);
            }
        });

        filter = false;
        dao = new ToDoItemDAO(this);
        tasks = dao.getAllToDoItems();

        adapter = new ToDoListAdapter(this, R.layout.listview_item, tasks);
        ListView lvTasks = findViewById(R.id.lvToDoItems);
        lvTasks.setAdapter(adapter);

        registerForContextMenu(lvTasks);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ToDoItem ti = adapter.getItem(info.position);

        switch (item.getItemId()) {
            case R.id.mi_edit:
                Intent intent = new Intent(getApplicationContext(), ToDoItemActivity.class);
                intent.putExtra("ti", ti);
                intent.putExtra("index", info.position);
                startActivityForResult(intent, REQC_EDIT);
                return true;
            case R.id.mi_remove:
                adapter.remove(ti);
                dao.deleteToDoItem(ti);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        int id = item.getItemId();

        if (id == R.id.mi_removeAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.confirmDeleteTitle).setMessage(R.string.confirmDelete)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dao.deleteAllToDoItems();
                    tasks.clear();
                    adapter.clear();

                }
            });

            AlertDialog ad = builder.create();
            ad.show();

            return true;
        } else if (id == R.id.mi_activeTasks) {
            filter = true;
            adapter.clear();
            tasks = dao.getActiveToDos();
            adapter.addAll(tasks);

            return true;
        } else if (id == R.id.mi_completedTasks) {
            filter = true;
            adapter.clear();
            tasks = dao.getCompletedToDos();
            adapter.addAll(tasks);

            return true;
        } else if (id == R.id.mi_allTasks) {
            filter = true;
            adapter.clear();
            tasks = dao.getAllToDoItems();
            adapter.addAll(tasks);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            ToDoItem ti = (ToDoItem) data.getSerializableExtra("ti");
            if (requestCode == REQC_NEW) {
                adapter.add(ti);
                if (filter) {
                    tasks.add(ti);
                }
                dao.saveToDoItem(ti);
            } else if (requestCode == REQC_EDIT) {
                int index = data.getIntExtra("index", -1);

                tasks.set(index, ti);
                dao.saveToDoItem(ti);

                if (filter) {
                    adapter.clear();
                    adapter.addAll(tasks);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
