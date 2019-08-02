package com.project.june.qrcode.special.bean;

/**
 * 素材
 * <p>
 * 素材包含素材的宽高比例以及素材对应的文件资源id
 * <p>
 * 目前已知的宽高比有六种
 * 1*1  1*2  1*3
 * 2*1  2*2
 * 3*1
 */
public class MaterialBean {

    //素材宽度比例
    public int widthScale;

    //素材高度比例
    public int heightScale;

    //素材ID
    public int materialResourceId;

    public String spec;

    public MaterialBean() {
    }

    public MaterialBean(int widthScale, int heightScale, int materialResourceId) {
        this.widthScale = widthScale;
        this.heightScale = heightScale;
        this.materialResourceId = materialResourceId;
        this.spec = getSpec(widthScale, heightScale);
    }

    public String getSpec(int width, int height) {
        return width + "*" + height;
    }
}
