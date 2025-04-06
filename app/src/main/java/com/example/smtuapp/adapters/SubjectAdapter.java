package com.example.smtuapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smtuapp.R;
import com.example.smtuapp.models.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private final Context context;
    private final List<Subject> subjectList;
    private final OnNoteClickListener noteClickListener;

    // Интерфейс для обработки клика по кнопке заметки
    public interface OnNoteClickListener {
        void onNoteClick(int position);
    }

    public SubjectAdapter(Context context, List<Subject> subjectList, OnNoteClickListener listener) {
        this.context = context;
        this.subjectList = subjectList;
        this.noteClickListener = listener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subject_item, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);

        holder.tvSubjectName.setText(subject.getName());
        holder.tvRoom.setText(subject.getRoom());
        holder.tvStartTime.setText(subject.getStartTime());
        holder.tvEndTime.setText(subject.getEndTime());

        // Показываем текст заметки или предложение добавить новую
        String note = subject.getNote();
        holder.btnNote.setText((note != null && !note.isEmpty()) ? note : "Добавить заметку");

        // Обработка клика на кнопку заметки
        holder.btnNote.setOnClickListener(v -> {
            if (noteClickListener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                noteClickListener.onNoteClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    // ViewHolder для элемента расписания
    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName, tvRoom, tvStartTime, tvEndTime;
        Button btnNote;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            btnNote = itemView.findViewById(R.id.btnNote);
        }
    }
}
