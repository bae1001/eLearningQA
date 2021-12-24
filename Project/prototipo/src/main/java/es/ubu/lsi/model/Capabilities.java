package es.ubu.lsi.model;

public class Capabilities{
    private boolean view;
    private boolean edit;
    private boolean delete;
    private boolean split;
    private boolean reply;
    private boolean selfenrol;
    private boolean export;
    private boolean controlreadstatus;
    private boolean canreplyprivately;

    public Capabilities() {}

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public boolean isSelfenrol() {
        return selfenrol;
    }

    public void setSelfenrol(boolean selfenrol) {
        this.selfenrol = selfenrol;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public boolean isControlreadstatus() {
        return controlreadstatus;
    }

    public void setControlreadstatus(boolean controlreadstatus) {
        this.controlreadstatus = controlreadstatus;
    }

    public boolean isCanreplyprivately() {
        return canreplyprivately;
    }

    public void setCanreplyprivately(boolean canreplyprivately) {
        this.canreplyprivately = canreplyprivately;
    }
}
