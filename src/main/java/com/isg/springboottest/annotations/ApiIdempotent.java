package com.isg.springboottest.annotations;

import org.assertj.core.util.diff.Delta;
import org.omg.SendingContext.RunTime;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD,TYPE})
public @interface ApiIdempotent {
}
