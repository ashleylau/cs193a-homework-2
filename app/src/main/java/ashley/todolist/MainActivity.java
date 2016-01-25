package ashley.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private ArrayList<String> taskItems;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Restores array list from previous state (if it exists), otherwise creates a new one
        if (savedInstanceState != null && savedInstanceState.containsKey("savedTasks")){
            taskItems = savedInstanceState.getStringArrayList("savedTasks");
        } else {
            taskItems = new ArrayList<>();
        }

        //Initializes the adapter for the listview and the array list
        adapter = new ArrayAdapter<>(
                this,
                R.layout.tasklayout,
                R.id.task_Item,
                taskItems
        );

        ListView taskList = (ListView) findViewById(R.id.task_List);
        taskList.setOnItemLongClickListener(this);
        taskList.setAdapter(adapter);
    }

    /* On pressing the add button, the text is gotten from the EditText field and that text
    is added to the array list of strings.
     */
    public void addTask(View view) {
        EditText toDoField = (EditText) findViewById(R.id.userTask);
        String toDoTask = toDoField.getText().toString();
        if (toDoTask.equals("")){
            Toast.makeText(MainActivity.this, "Enter some text!", Toast.LENGTH_SHORT).show();
        } else {
            taskItems.add(toDoTask);
            adapter.notifyDataSetChanged();
            toDoField.setText("");
        }
    }

    /* On a long click, the to-do task is deleted. A message is shown to indicate that to the user.
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this,
                "Deleted: " + taskItems.get(position),
                Toast.LENGTH_SHORT).show();
        taskItems.remove(position);
        adapter.notifyDataSetChanged();
        return false;
    }

    /* Saves the tasks with the key "savedTasks" */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("savedTasks", taskItems);
    }

    /*Restores the to-do tasks upon restoration of the activity. This method
     * also recreates the adapter and list view. */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        taskItems = savedInstanceState.getStringArrayList("savedTasks");
        adapter = new ArrayAdapter<>(
                this,
                R.layout.tasklayout,
                R.id.task_Item,
                taskItems
        );

        ListView taskList = (ListView) findViewById(R.id.task_List);
        taskList.setOnItemLongClickListener(this);
        taskList.setAdapter(adapter);
    }


}