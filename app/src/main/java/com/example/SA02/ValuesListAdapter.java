package com.example.SA02;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

    public class ValuesListAdapter extends RecyclerView.Adapter<ValuesListAdapter.ValuesViewHolder> {

        private final LayoutInflater mInflater;
        private List<Values> mValues; // Cached copy of values
        public static final String EXTRA_MESSAGE_KEY = "com.example.android.SA02.extra.MESSAGE";
        private WeakReference<Context> weakContext;

        ValuesListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.weakContext = new WeakReference<>(context); }

        @Override
        public ValuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
            return new ValuesViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ValuesViewHolder holder, final int position) {

            if (mValues != null) {
                final Values current = mValues.get(position);
                holder.valueItemView.setText(current.getDate());

                holder.valueItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(weakContext.get(), ShowData.class);
                        int extra = current.getId();
                        intent.putExtra(EXTRA_MESSAGE_KEY, extra);
                        weakContext.get().startActivity(intent);
                    }
                });
            } else {
                // Covers the case of data not being ready yet.
                holder.valueItemView.setText("No Values");
            }
        }

        void setValues(List<Values> values){
            mValues = values;
            notifyDataSetChanged();
        }

        // getItemCount() is called many times, and when it is first called,
        // mValues has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (mValues != null)
                return mValues.size();
            else return 0;
        }

        class ValuesViewHolder extends RecyclerView.ViewHolder {
            private final TextView valueItemView;

            private ValuesViewHolder(View itemView) {
                super(itemView);
                valueItemView = itemView.findViewById(R.id.textView);
            }
        }

        public Values getValueAtPosition (int position) {
            return mValues.get(position);
        }
    }

