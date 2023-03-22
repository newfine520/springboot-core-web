package com.isg.springboottest.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName com.shfe.yqservice.constants.CategoryCons
 * @Description excel表格中产品代码转换
 * @Author lidong
 * @date 2020/8/13
 **/
public enum CategoryEnum {

    AG_F("白银", "ag_f"),
    AL_F("铝", "al_f"),
    AU_F("黄金", "au_f"),
    AU_O("黄金期权", "au_o"),
    BU_F("沥青", "bu_f"),
    CU_O("铜期权", "cu_o"),
    CU_F("铜", "cu_f"),
    FU_F("燃料油", "fu_f"),
    HC_F("热轧卷板", "hc_f"),
    NI_F("镍", "ni_f"),
    PB_F("铅", "pb_f"),
    RB_F("螺纹钢", "rb_f"),
    RU_O("天胶期权", "ru_o"),
    RU_F("橡胶", "ru_f"),
    SC_F("原油/上海国际能源交易公司", "sc_f"),
    SN_F("锡", "sn_f"),
    SP_F("纸浆期货", "sp_f"),
    WR_F("线材", "wr_f"),
    ZN_F("锌", "zn_f"),
    NR_F("20号胶", "nr_f"),
    LU_F("低硫燃料油", "lu_f"),
    SS_F("不锈钢", "ss_f"),
    AL_O("铝期权", "al_o"),
    ZN_O("锌期权", "zn_o"),
    BC_F("国际铜", "bc_f"),
    SC_INE("原油（INE）", "sc_f"),
    BC_INE("国际铜（INE）", "bc_f"),
    NR_INE("20号胶（INE）", "nr_f"),
    LU_INE("低硫燃料油（INE）", "lu_f"),
    SC_O("原油期权", "sc_o");


    private String category;
    private String desc;

    CategoryEnum(String desc, String category) {
        this.category = category;
        this.desc = desc;
    }

    private static Map<String, String> map = new HashMap<>();

    static {
        for (CategoryEnum e : CategoryEnum.values()) {
            map.put(e.getDesc(), e.getCategory());
        }
    }

    public static String getCatecode(final String desc) {
        return map.get(desc) == null ? "other" : map.get(desc);
    }

    public String getCategory() {
        return category;
    }

    public String getDesc() {
        return desc;
    }

}
