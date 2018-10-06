package com.kalam.sibi.ontime;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Sibi on 25-02-2018.
 */

public class ChangeFont {

    public static void changeFont(Context ctx,String defaultFont,String newFont){

        try{
            Typeface customFont = Typeface.createFromAsset(ctx.getAssets(),newFont);
            Field oldFont = Typeface.class.getDeclaredField(defaultFont);
            oldFont.setAccessible(true);
            oldFont.set(null,customFont);
        }catch (Exception e){
                Log.d("FONT","ERROR");
        }
    }
}
