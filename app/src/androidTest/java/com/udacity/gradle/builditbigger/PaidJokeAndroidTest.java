package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class PaidJokeAndroidTest {

    @Test
    public void testGetJokeAsyncTask() {
        String joke;
        try {
            joke = new GetJokeAsyncTask().execute().get();
        } catch (Exception e) {
            joke = null;
        }

        assertNotNull(joke);
    }
}
