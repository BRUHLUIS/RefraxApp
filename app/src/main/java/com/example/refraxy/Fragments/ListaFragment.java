package com.example.refraxy.Fragments;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.refraxy.R;

import java.util.ArrayList;
import java.util.List;

public class ListaFragment extends Fragment {

    private LinearLayout tasksContainer;
    private EditText editTextNewTask;
    private Button buttonAddTask;

    private List<String> tasksList;
    private TasksAdapter tasksAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        tasksContainer = view.findViewById(R.id.tasksContainer);
        editTextNewTask = view.findViewById(R.id.editTextNewTask);
        buttonAddTask = view.findViewById(R.id.buttonAddTask);

        tasksList = new ArrayList<>();
        tasksAdapter = new TasksAdapter(tasksList);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTask = editTextNewTask.getText().toString().trim();
                if (!newTask.isEmpty()) {
                    tasksList.add(newTask);
                    tasksAdapter.notifyDataSetChanged();
                    editTextNewTask.setText("");
                } else {
                    Toast.makeText(getActivity(), "Ingrese una tarea", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tasksContainer.removeAllViews();
        for (String task : tasksList) {
            tasksAdapter.addTaskView(task);
        }

        return view;
    }

    private class TasksAdapter {

        private List<String> tasks;

        public TasksAdapter(List<String> tasks) {
            this.tasks = tasks;
        }

        public void notifyDataSetChanged() {
            tasksContainer.removeAllViews();
            for (String task : tasks) {
                addTaskView(task);
            }
        }

        private void addTaskView(final String task) {
            View taskView = getLayoutInflater().inflate(R.layout.fragment_task, null);
            TextView textViewTask = taskView.findViewById(R.id.textViewTask);
            textViewTask.setText(task);

            // Create a new LinearLayout to wrap the task view and set its layout parameters
            LinearLayout taskContainer = new LinearLayout(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int margin = getResources().getDimensionPixelSize(R.dimen.task_margin);
            layoutParams.setMargins(margin, margin, margin, margin);
            taskContainer.setLayoutParams(layoutParams);

            // Set the background drawable to create the circular shape for the task
            taskContainer.setBackgroundResource(R.drawable.round_button);
            taskContainer.addView(taskView);

            textViewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTask(task);
                }
            });
            textViewTask.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showEditDialog(task);
                    return true;
                }
            });
            tasksContainer.addView(taskContainer);
        }

        public void deleteTask(final String task) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Eliminar tarea");
            builder.setMessage("¿Estás seguro de que quieres eliminar esta tarea?");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tasks.remove(task);
                    notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }

        private void showEditDialog(final String task) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Editar tarea");
            final EditText editText = new EditText(getActivity());
            editText.setText(task);
            builder.setView(editText);
            builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String updatedTask = editText.getText().toString().trim();
                    if (!updatedTask.isEmpty()) {
                        editTask(task, updatedTask);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Ingrese una tarea", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }

        private void editTask(String task, String updatedTask) {
            int index = tasks.indexOf(task);
            if (index != -1) {
                tasks.set(index, updatedTask);
                notifyDataSetChanged();
            }
        }
    }
}

