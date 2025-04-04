package com.example.smtuapp;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smtuapp.adapters.SubjectAdapter;
import com.example.smtuapp.models.Subject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity implements SubjectAdapter.OnNoteClickListener {

    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private List<Subject> subjectList;
    private LinearLayout dayCarousel;
    private TextView tvWeekType;
    private TextView tvGroupNumber;
    private BottomNavigationView bottomNavigationView;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // Инициализация компонентов
        recyclerView = findViewById(R.id.rvSubjects);
        tvWeekType = findViewById(R.id.tvWeekType);
        tvGroupNumber = findViewById(R.id.tvGroupNumber);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        topAppBar = findViewById(R.id.topAppBar);

        // Настройка тулбара
        topAppBar.setNavigationOnClickListener(v ->
                Toast.makeText(this, "Открыть настройки", Toast.LENGTH_SHORT).show());

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

        // Настройка списка пар
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectList = getDummySubjects();
        adapter = new SubjectAdapter(this, subjectList, this);
        recyclerView.setAdapter(adapter);

        // Пример заполнения блока с неделей и группой
        tvWeekType.setText("Верхняя неделя");
        tvGroupNumber.setText("Группа 20150");

        // Добавление дней недели в карусель
        //TODO ЛОМАЕТ ПРИЛОЖЕНИЕ
        addDaysToCarousel();
    }

    private void addDaysToCarousel() {
        String[] days = {"ПН", "ВТ", "СР", "ЧТ", "ПТ"};
        String[] dates = {"07.02", "08.02", "09.02", "10.02", "11.02"};

        for (int i = 0; i < days.length; i++) {
            LinearLayout dayItem = new LinearLayout(this);
            dayItem.setOrientation(LinearLayout.VERTICAL);
            dayItem.setGravity(Gravity.CENTER);
            dayItem.setPadding(16, 16, 16, 16);

            TextView tvDay = new TextView(this);
            tvDay.setText(days[i]);
            tvDay.setTextSize(16);
            tvDay.setTextColor(getResources().getColor(R.color.black));

            TextView tvDate = new TextView(this);
            tvDate.setText(dates[i]);
            tvDate.setTextSize(14);
            tvDate.setTextColor(getResources().getColor(R.color.black));

            dayItem.addView(tvDay);
            dayItem.addView(tvDate);

            // Добавление дня в карусель
            dayCarousel.addView(dayItem);
        }
    }

    private List<Subject> getDummySubjects() {
        List<Subject> list = new ArrayList<>();
        list.add(new Subject("Математика", "205", "08:30", "10:00", ""));
        list.add(new Subject("Физика", "301", "10:10", "11:40", ""));
        list.add(new Subject("Программирование", "101", "11:50", "13:20", "Подготовиться к лабе"));
        return list;
    }

    @Override
    public void onNoteClick(int position) {
        Subject subject = subjectList.get(position);
        Toast.makeText(this, "Заметка для: " + subject.getName(), Toast.LENGTH_SHORT).show();

        // TODO: запускать активити редактирования заметкокк
    }
}
