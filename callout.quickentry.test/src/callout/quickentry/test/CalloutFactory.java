package callout.quickentry.test;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.adempiere.webui.component.Messagebox;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MBPartner;
import org.compiere.util.CLogger;

public class CalloutFactory implements IColumnCalloutFactory {
	
	private static CLogger log = CLogger.getCLogger(CalloutFactory.class);
	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {
		log.warning("CalloutFactory here.");
		
		if (tableName.equalsIgnoreCase(MBPartner.Table_Name)) {
			if (columnName.equalsIgnoreCase(MBPartner.COLUMNNAME_Name)) {
				return new IColumnCallout[]{new CalloutTest()};
			}			
		}
		return null;
	}
	
	static private class CalloutTest implements IColumnCallout {
		static int counter = 0;

		@Override
		public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {
			
			// Don't show msgBox with new record.
			int bpartnerID = fieldToInt(mTab.getValue(MBPartner.COLUMNNAME_C_BPartner_ID));
			if(bpartnerID  < 1 && value == null) return null;
			
			
			// Show value and oldValue. These should be different.
			Messagebox.showDialog("value: " + (String)value + "       oldValue: " + (String) oldValue, 
					"CalloutTest", 
					Messagebox.OK, 
					Messagebox.EXCLAMATION);
			log.warning("counter: " + counter);
			++counter;
			return null;
		}
	}
	
	public static int fieldToInt(Object fieldVal) {
		if (fieldVal == null) return 0;
		int intVal = ((Integer) fieldVal).intValue();
		return intVal < 0 ?  0 : intVal;		
	}
}
