package com.enorth.dns.dnshosts.vo;/*
 * @author  Administrator
 * @description:
 * @date 2019/6/17
 * */

import com.enorth.dns.dnshosts.vo.base.PhotoVo;

import java.io.Serializable;
import java.util.List;

public class sysLogVo extends com.enorth.dns.dnshosts.vo.base.sysLogVo implements Serializable {

    private List<PhotoVo> photos;

    public List<PhotoVo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoVo> photos) {
        this.photos = photos;
    }
}
