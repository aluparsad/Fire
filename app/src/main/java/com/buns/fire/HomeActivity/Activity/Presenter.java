package com.buns.fire.HomeActivity.Activity;

import androidx.annotation.NonNull;

import com.buns.fire.Models.Subscription;
import com.buns.fire.Utils.SingletonUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Presenter implements HomeContractor.Presenter {
    private HomeContractor.View mView;

    public Presenter(@NonNull HomeContractor.View mView) {
        this.mView = mView;
    }

    @Override
    public void getIsEligible() throws ParseException {
        mView.ShowLoading();
        Subscription plan = SingletonUser.getInstance().getUser().getPlan();
        mView.isEligible((plan != null) ? (!plan.equals(Subscription.VISITOR) || checkDate()) : checkDate());
    }

    private boolean checkDate() throws ParseException {
        long authDate = SingletonUser.getInstance().getUser().getTimeAuth();
        long currentDate = Calendar.getInstance().getTimeInMillis();

        String initDate = getDateFromMS(authDate);
        String finalDate = getDateFromMS(currentDate);

        return Math.round(getDateDifference(initDate, finalDate)) <= 5;
    }

    private String getDateFromMS(final long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return sdf.format(calendar.getTime());
    }

    private long getDateDifference(final String initialdate, final String finalDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date first = sdf.parse(initialdate);
        Date last = sdf.parse(finalDate);

        long diffInMillies = Math.abs(last.getTime() - first.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
