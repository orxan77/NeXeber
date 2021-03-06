package com.example.orkhan.nexeber.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.orkhan.nexeber.MainActivity;
import com.example.orkhan.nexeber.Models.News;
import com.example.orkhan.nexeber.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Orkhan on 3/23/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context mContext;
    private List<News> mNewsList;

    public NewsAdapter(Context context, List<News> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new NewsViewHolder(view);
    }

    private static String getWebsite(int websiteId) {
        switch (websiteId) {
            case 1:
                return "Milli.az";
            case 2:
                return "Azernews.az";
            case 3:
                return "News.az";
            case 4:
                return "Today.az";
            case 5:
                return "Metbuat.az";
            case 6:
                return "Apa.az";
            case 8:
                return "1news.az";
            case 9:
                return "Banker.az";
            case 10:
                return "Day.az";
            case 11:
                return "Report.az";
            case 13:
                return "Oxu.az";
            case 14:
                return "Haqqin.az";
            default:
                return "";
        }
    }

    private static String theMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        final News currentItem = mNewsList.get(position);

        String title = currentItem.getTitle();
        String description = currentItem.getDescription();

        if (description.isEmpty())
            holder.mDescriptionTextView.setVisibility(View.GONE);

        if (description != null && description.length() > 80) {
            description = description.substring(0, 80);
            description += "...";
        }

        String date = currentItem.getCreateDate();
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(date));
            long timeOfNewsInMillis = calendar.getTimeInMillis();
            long currentTimeInMillis = System.currentTimeMillis();
            long timeDifference = currentTimeInMillis - timeOfNewsInMillis;
            long timeDifferenceInMinutes = timeDifference / 1000 / 60;
            long timeDifferenceInHours = timeDifferenceInMinutes / 60;

            if (timeDifferenceInMinutes <= 30) {
                date = timeDifferenceInMinutes + " minutes ago";
            } else if (timeDifferenceInMinutes > 30 && timeDifferenceInMinutes <= 1440) { //1440 minutes equal to 1 day.
                if (timeDifferenceInHours == 1)
                    date = timeDifferenceInHours + " hour ago";
                else
                    date = timeDifferenceInHours + " hours ago";
            } else if (timeDifferenceInMinutes > 1440) {
                date = calendar.get(Calendar.DAY_OF_MONTH) + " " + theMonth(calendar.get(Calendar.MONTH));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String imageUrl = currentItem.getImageUrl();

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo);
        Glide.with(mContext)
                .load(imageUrl)
                .apply(requestOptions)
                .into(holder.mNewsImageView);


        holder.mListItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                String newsUrl = currentItem.getNewsUrl();
                if (newsUrl == null || newsUrl.equals("") || newsUrl.isEmpty()) {
                    Toast.makeText(mContext, "WebView for selected news is nt available", Toast.LENGTH_SHORT).show();
                } else {
                    customTabsIntent.launchUrl((MainActivity) mContext, Uri.parse(newsUrl));
                }
//                Intent intent = new Intent(mContext, WebViewActivity.class);
//                String newsUrl = currentItem.getNewsUrl();
//                if (newsUrl == null || newsUrl.equals("") || newsUrl.isEmpty()) {
//                    Toast.makeText(mContext, "WebView for selected news is nt available", Toast.LENGTH_SHORT).show();
//                } else {
//                    intent.putExtra("newsUrl", newsUrl);
//                    mContext.startActivity(intent);
//                }
            }
        });

        int websiteId = currentItem.getWebsiteId();
        String website = getWebsite(websiteId);

        holder.mNewsWebsite.setText(website);
        holder.mDateTextView.setText(date);
        holder.mTitleTextView.setText(title);
        holder.mDescriptionTextView.setText(description);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private TextView mDateTextView;
        private ImageView mNewsImageView;
        private CardView mListItemCardView;
        private TextView mNewsWebsite;

        public NewsViewHolder(View itemView) {

            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.news_title);
            mDescriptionTextView = itemView.findViewById(R.id.news_description);
            mDateTextView = itemView.findViewById(R.id.news_date);
            mNewsImageView = itemView.findViewById(R.id.news_imageview);
            mListItemCardView = itemView.findViewById(R.id.news_card_view);
            mNewsWebsite = itemView.findViewById(R.id.news_website);
        }
    }
}
