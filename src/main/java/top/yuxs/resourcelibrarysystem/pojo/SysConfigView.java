package top.yuxs.resourcelibrarysystem.pojo;

import lombok.Data;

@Data
public class SysConfigView {
    private boolean isSysLog;
    private boolean  isIpLog;
    private boolean  isUserLoginLog;
    private boolean  isPageLog;
}
