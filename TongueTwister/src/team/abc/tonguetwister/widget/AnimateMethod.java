package team.abc.tonguetwister.widget;

/*
 * 
 * 供RangeSliderView使用
 * 
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface AnimateMethod {
}
