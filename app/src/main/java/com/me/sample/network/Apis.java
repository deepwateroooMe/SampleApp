package com.me.sample.network;

public class Apis { // interface methods

    // 下面这个是需要的，因为需要从网络上去下载各个员工的图片
    @GET
    @Streaming
    Observable downloadImg(@Url String imgUrl);

    
}
