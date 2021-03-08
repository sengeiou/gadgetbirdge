package nodomain.freeyourgadget.gadgetbridge.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import nodomain.freeyourgadget.gadgetbridge.GBApplication;
import nodomain.freeyourgadget.gadgetbridge.GBException;
import nodomain.freeyourgadget.gadgetbridge.devices.DeviceCoordinator;
import nodomain.freeyourgadget.gadgetbridge.devices.SampleProvider;
import nodomain.freeyourgadget.gadgetbridge.impl.GBDevice;
import nodomain.freeyourgadget.gadgetbridge.model.ActivitySample;

public class DeviceData {
    GBDevice device;
    //这样还不是很主动的策略，感觉还是要看看coordinate的具体获得方法吧。。。。
    public DeviceData(GBDevice out){
        device=out;
    }

    private ActivitySample exCsv() throws GBException, IOException {
        long i = 1;

        DeviceCoordinator coordinator = DeviceHelper.getInstance().getCoordinator(device);
        SampleProvider<? extends ActivitySample> provider = coordinator.getSampleProvider(device, GBApplication.acquireDB().getDaoSession());
        Date startdate=new Date(121,0,25);
        Date enddate = new Date();
        for (Date date = startdate; date.getTime() < enddate.getTime(); ) {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);
            cal.add(GregorianCalendar.DAY_OF_YEAR, 3);
            Date newDate = cal.getTime();
            if (newDate.getTime() >= enddate.getTime()) {
                newDate = enddate;
            }
            int start=new Long(date.getTime()/1000).intValue();
            int end=new Long(newDate.getTime()/1000).intValue();
            List<? extends ActivitySample> samples = provider.getAllActivitySamples(start, end);
            date = newDate;
            for (ActivitySample sample : samples) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("TimeStamp",sample.getTimestamp()+"");
                    obj.put("Steps",sample.getSteps() + "");
                    obj.put("HeartRate",sample.getHeartRate() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        return null;
    }

}
