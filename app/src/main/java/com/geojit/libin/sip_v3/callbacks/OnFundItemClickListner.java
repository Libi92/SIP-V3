package com.geojit.libin.sip_v3.callbacks;

import com.geojit.libin.sip_v3.excelreader.FundModal;
import com.geojit.libin.sip_v3.modals.FundDataModal;

/**
 * Created by 10945 on 8/10/2016.
 */
public interface OnFundItemClickListner {
    void onClick(final FundDataModal model, final FundModal performanceModal);
}
