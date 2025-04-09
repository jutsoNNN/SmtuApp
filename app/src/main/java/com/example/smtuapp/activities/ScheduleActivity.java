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
import java.util.Calendar;
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
    private int selectedDayIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initViews();
        initToolbar();
        initRecyclerView();

        tvWeekType.setText("Верхняя неделя");
        tvGroupNumber.setText("Группа 20150");

        // Настройка нижней навигации
        bottomNavigationView.setSelectedItemId(R.id.nav_schedule);  // Выделить "Расписание" по умолчанию

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_schedule) {
                Toast.makeText(this, "Вы уже на этом экране", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.nav_navigation) {
                Intent navigationIntent = new Intent(ScheduleActivity.this, NavigationActivity.class);
                navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigationIntent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_isu) {
                Intent isuIntent = new Intent(ScheduleActivity.this, ISUActivity.class);
                isuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(isuIntent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_services) {
                Intent servicesIntent = new Intent(ScheduleActivity.this, ServicesActivity.class);
                servicesIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(servicesIntent);
                finish();
                return true;
            } else {
                return false;
            }
        });


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
        // Получаем текущий день недели, корректируем для индексации с понедельника (ПН - 0, ВТ - 1, ..., ВС - 6)
        int currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        // Преобразуем день недели так, чтобы ПН был 0, ВТ — 1, ..., СБ — 5, ВС — 6
        if (currentDayOfWeek == Calendar.SUNDAY) {
            currentDayOfWeek = 6; // Если воскресенье, то индекс 6
        } else {
            currentDayOfWeek -= 2; // Если не воскресенье, уменьшаем на 2, чтобы ПН был 0
        }

        for (int i = 0; i < dayLabels.length; i++) {
            String day = dayLabels[i];

            TextView dayView = new TextView(this);
            dayView.setText(day);
            dayView.setPadding(32, 20, 32, 20);
            dayView.setTextSize(16);
            dayView.setGravity(Gravity.CENTER);
            dayView.setTextColor(ContextCompat.getColor(this, R.color.black));
            dayView.setBackgroundResource(R.drawable.day_background);

            // Выделяем текущий день (сравниваем с currentDayOfWeek)
            if (i == currentDayOfWeek) {
                dayView.setTextColor(ContextCompat.getColor(this, R.color.white)); // белый текст
                dayView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)); // фоновый цвет
                selectedDayIndex = i; // Устанавливаем текущий день как выбранный
            }

            // Обработчик нажатия на день
            int finalI = i;
            dayView.setOnClickListener(v -> {
                // Обновляем выбранный день
                selectedDayIndex = finalI;
                loadSubjectsForDay(day);
                updateDaySelection(); // Обновляем выделение
            });

            dayCarousel.addView(dayView);
        }
    }

    // Метод для обновления выделения выбранного дня
    private void updateDaySelection() {
        for (int i = 0; i < dayCarousel.getChildCount(); i++) {
            TextView dayView = (TextView) dayCarousel.getChildAt(i);

            // Если это выбранный день, выделяем его
            if (i == selectedDayIndex) {
                dayView.setTextColor(ContextCompat.getColor(this, R.color.white)); // белый текст
                dayView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)); // фоновый цвет
            } else {
                dayView.setTextColor(ContextCompat.getColor(this, R.color.black)); // обычный текст
                dayView.setBackgroundResource(R.drawable.day_background); // стандартный фон
            }
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
