package com.example.smtuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smtuapp.R;
import com.example.smtuapp.adapters.SubjectAdapter;
import com.example.smtuapp.models.Subject;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleActivity extends AppCompatActivity implements SubjectAdapter.OnNoteClickListener {

    private static final int REQUEST_CODE_ADD_NOTE = 1;

    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private List<Subject> subjectList = new ArrayList<>();
    private LinearLayout dayCarousel;
    private TextView tvWeekType;
    private TextView tvGroupNumber;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar topAppBar;

    private Map<String, List<Subject>> scheduleByDay = new HashMap<>();

    private String[] dayLabels = {"ПН", "ВТ", "СР", "ЧТ", "ПТ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initViews();
        initToolbar();
        initRecyclerView();

        tvWeekType.setText("Верхняя неделя");
        tvGroupNumber.setText("Группа 20150");

//        topAppBar.setOnMenuItemClickListener(item -> {
//            if (item.getItemId() == R.id.action_search) {
//                Toast.makeText(this, "Поиск...", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//            return false;
//        });

//        // Настройка нижней навигации
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_schedule:
//                    // на этом экране уже
//                    return true;
//                case R.id.nav_grades:
//                    Toast.makeText(this, "Оценки", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.nav_news:
//                    Toast.makeText(this, "Новости", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.nav_profile:
//                    Toast.makeText(this, "Профиль", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//                    return false;
//            }
//        });

        setupWeeklySchedule();       // Наполняем расписание
        addDaysToCarousel();         // Динамически добавляем дни
        loadSubjectsForDay("ПН");    // Загружаем ПН по умолчанию
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rvSubjects);
        dayCarousel = findViewById(R.id.dayCarousel);
        tvWeekType = findViewById(R.id.tvWeekType);
        tvGroupNumber = findViewById(R.id.tvGroupNumber);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        topAppBar = findViewById(R.id.topAppBar);
    }

    private void initToolbar() {
        topAppBar.setNavigationOnClickListener(v ->
                Toast.makeText(this, "Открыть настройки", Toast.LENGTH_SHORT).show());
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectAdapter(this, subjectList, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupWeeklySchedule() {
        for (String day : dayLabels) {
            List<Subject> dailySchedule = new ArrayList<>();
            dailySchedule.add(new Subject("Математика", "205", "", "08:30", "10:00"));
            dailySchedule.add(new Subject("Физика", "302", "", "10:10", "11:40"));
            scheduleByDay.put(day, dailySchedule);
        }
    }

    private void addDaysToCarousel() {
        for (String day : dayLabels) {
            TextView dayView = new TextView(this);
            dayView.setText(day);
            dayView.setPadding(32, 20, 32, 20);
            dayView.setTextSize(16);
            dayView.setGravity(Gravity.CENTER);
            dayView.setTextColor(ContextCompat.getColor(this, R.color.black));
            dayView.setBackgroundResource(R.drawable.day_background);

            dayView.setOnClickListener(v -> {
                loadSubjectsForDay(day);
            });

            dayCarousel.addView(dayView);
        }
    }

    private void loadSubjectsForDay(String dayKey) {
        List<Subject> subjects = scheduleByDay.get(dayKey);
        subjectList.clear();

        if (subjects != null) {
            subjectList.addAll(subjects);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteClick(int position) {
        Subject subject = subjectList.get(position);
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra("subject_position", position);
        startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            int position = data.getIntExtra("subject_position", -1);
            String noteText = data.getStringExtra("note");

            if (position != -1 && noteText != null) {
                subjectList.get(position).setNote(noteText);
                adapter.notifyItemChanged(position);
                Toast.makeText(this, "Заметка успешно сохранена", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
