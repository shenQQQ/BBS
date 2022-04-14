package indi.shenqqq.bbs.enums;

/**
 * @Author Shen Qi
 * @Date 2022/4/7 18:51
 * @Description XX
 */
public enum FileSuffix {
    JPG(".jpg"),JPEG(".jpeg"), PNG(".png"), GIF(".gif"), MP4(".mp4");

    private String type;

    FileSuffix(String s) {
        this.type = s;
    }

    public static String[] valueArr(){
        FileSuffix[] arr = FileSuffix.values();
        String[] result = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i].getType();
        }
        return result;
    }

    public String getType() {
        return type;
    }
}
