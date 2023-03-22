package com.isg.springboottest.annotations;

public interface MyValidHandler<T> {
    boolean valid(MyValid myValid, T data);
}
