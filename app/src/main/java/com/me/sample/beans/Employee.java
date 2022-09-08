package com.me.sample.beans;

public class Employee extends BaseObservable {
    public String name;
    public String imgUrl;

    @Bindable
        public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    // @Bindable
    public String getImageUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        // notifyPropertyChanged(BR.name);
    }
    
    public Employee(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
