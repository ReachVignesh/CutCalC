package com.ru.cutcalc;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Cut_calc extends Activity {

	SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(this);
	private float F_totalSpan = 1500;
	private float F_redSpan = 500;
	private float F_blueSpan = 500;
	private float F_greySpan = 500;

	Drawable D_grayOffset;
	Drawable D_greenOffset;
	Drawable D_redOffset;
	private CustomSeekBar Seekbar_withofcut;
	private CustomSeekBar Seekbar_depthofcut;
	private CustomSeekBar Seekbar_backtospeed;
	private CustomSeekBar Seekbar_feedrateseek;
	
	Spinner Spinner_typeofmills;
	Spinner Spinner_workmaterial;// for material type
	Spinner Spinner_flutecount;// for flute count
	Spinner Spinner_diameter;// for diameter
	Spinner Spinner_hardness;
	Spinner Spinner_subtype;

	String  S_workmaterialPos, S_flutecountPos,
			S_hardnessPos, S_diameterItem,
			S_diameterPos, S_subtypePos;
	
	String S_imageOption;

	String S_shapeofcutId;

	String S_typeofCal;
	String S_millstypePos;
	String S_flutecountItem;

	int I_widthofcutprogress = 0;
	int I_depthofcutprogress = 0;
	int I_backtospeedprogress;
	int I_feedrateprogress;

	
	ImageView Img_material;
	ImageView Img_side;
	ImageView Img_slot;

	
	TextView Txt_depthofcut;
	TextView Txt_backtospeed;
	TextView Txt_feedrate;
	TextView Txt_widthofcut;
	TextView Txt_message;
	TextView Txt_millstype;
	TextView Txt_diameter;
	TextView Txt_subtype;
	TextView Txt_hardness;
	TextView Txt_shapeofcut;
	TextView Txt_widthofcutname;
	TextView Txt_depthofcutname;
	TextView Txt_backtospeedname;
	TextView Txt_feedratename;
	TextView Txt_workmaterialname;
	TextView Txt_flutecountname;
    TextView Txt_side;
    TextView Txt_slot;

	TextView Txt_max1;// txtv3max
	TextView Txt_max2;
	TextView Txt_max3;
	TextView Txt_max4;
	
	
	double Double_inputdia = 0.0;
	double Duble_widthofcutMax = 0.0;
	double Double_depthofcutMax = 0.0;
	double Double_backtospeedMax = 0.0;
	double Double_feedrateMax = 0.0;
	double Double_widthofcutIp = 0.0;
	double Double_depthofcutIp = 0.0;
	double Double_widthofcutOp = 0.0;
	double Double_depthofcutOp = 0.0;
	
	double Fz = 0.0;
	double N = 0.0;
	double Vf = 0.0;
	double Vc = 0.0;

	private View subtypeViewGroup;
	private View widthofcutViewGroup;//
	private View depthofViewGroup;//
	private View workmaterialViewGroup;
	private View flutecountViewGroup;
	private View diameterViewGroup;
	private View hardnessViewGroup;

	private View shapeofcutViewGroup;
	private View backtospeedViewGroup;
	private View feedrateViewGroup;

	

	public String[] work_mills_material = { "Aluminium Alloy",
			"Silicoaluminium Si≦10%" };// mills 2
	public String[] work_mills_material1 = { "Stainless Steel" };// mills 8
	public String[] work_mills_material2 = {
			"Carbon Steel,Alloy Steel-750N/mm2",
			"Carbon Steel,Alloy Steel-30HRC",
			"Pre-hardened Steel,Pre-heated Steel-40HRC" };// mills 15,16,17
	public String[] work_mills_material3 = { "Cast Iron",
			"Cast Iron, Alloy Steel – 750N/mm2",
			"Carbon steel, Alloy Steel – 30 HRC",
			"Pre-hardened Steel, Pre-heated Steel – 40 HRC", "Stainless Steel" };// 22
	public String[] work_mills_material4 = { "Select work material",
			"High hardness material" };

	public String[] work_metrialen = { "Cast Iron",
			"Cast Iron, Alloy Steel – 750N/mm2",
			"Carbon steel, Alloy Steel – 30 HRC",
			"Pre-hardened Steel, Pre-heated Steel – 40 HRC", "Stainless Steel",
			"Hardened Steel <52 HRC", "Aluminum Alloy",
			"Silicoaluminium Si<=10%" };

	public String[] type1_Diameter = { "3.0", "4.0", "5.0", "6.0", "8.0",
			"10.0", "12.0", "16.0", "20.0"

	};

	public String[] HRC = { "HRC 45", "HRC 55", "HRC 60"

	};

	public String[] pro_no = { "xAABN-", "xAAS-", "xAASLS-", "xBNM-", "xLSBN-",
			"60CD-", "xCRM-", "xLSCR-", "xCRC-", "2DEM-", "xSSS-", "xHREM-",
			"xSHH-", "2HSD-", "2HSDC-________-3xd", "2HSDC-________-5xd",
			"LGC-", "xLNSFSM-", "xLNSFCR-", "xLNSFBN-", "2MDD-", "xMBN-",
			"xMSEM-", "NCSD-", "xREM-", "1SEM-", "xSEM-", "xLSS-", "xLFS-",
			"TBNM-xF", "TSEM-xF", "HRxT", "HRxT_______X", "VGC-", "HRxT_S",
			"HRxT_R", "xWTREM", "xWTFLF", "xWTFEM"

	};

	public String[] mills_dia2 = { "3", "4", "5", "6", "8", "10", "12", "14",
			"16", "18", "20" };

	public String[] mills_dia8 = { "1", "2", "3", "4", "5", "6", "7", "8",
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };

	public String[] mills_dia15 = { "0.5", "0.8", "1", "1.2", "1.5", "2",
			"2.5", "3", "4", };

	public String[] mills_dia22 = { "6", "8", "10", "12", "14", "16", "18",
			"20" };

	public String[] subtype = { "Longer shank", "Longer blade" };
	public String[] subtype1 = { "Standard", "Longer shank" };
	public String[] subtype2 = { "Standard", "Longer shank", "Longer blade" };

	private ArrayList<ProgressItem> progressItemList;
	private ProgressItem mProgressItem;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
/* Remove Application label from layout screen. This condition used for above api level 10*/ 
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.xactivity_main);
		ActionBar actionBar = getActionBar();
		actionBar.show();
/* Remove Application label from layout screen*/

/* copying database from assert folder */
		try 
		{
			dbHelper.CopyDataBaseFromAsset();
		}
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
/* copying database from asset folder */

/* set the colour for the seek bar thumb */
		D_grayOffset = getResources().getDrawable(R.drawable.gray);
		D_greenOffset = getResources().getDrawable(R.drawable.green);
		D_redOffset = getResources().getDrawable(R.drawable.red);
/* set the colour for the seek bar thumb */

		
/* set the font style  from asset folder */	
		Typeface type = Typeface.createFromAsset(this.getAssets(),
				"CALIBRI.TTF");

		Txt_millstype = (TextView) findViewById(R.id.millstype);
		Txt_subtype = (TextView) findViewById(R.id.subtype);
		Txt_diameter = (TextView) findViewById(R.id.diam);
		Txt_shapeofcut = (TextView) findViewById(R.id.text_shape);
        Txt_side = (TextView) findViewById(R.id.side) ;
        Txt_slot = (TextView) findViewById(R.id.slot);
		Txt_hardness = (TextView) findViewById(R.id.hradness);

		Txt_workmaterialname = (TextView) findViewById(R.id.workmet);
		Txt_flutecountname = (TextView) findViewById(R.id.fcount);

		Txt_widthofcutname = (TextView) findViewById(R.id.woctxtv);
		Txt_depthofcutname = (TextView) findViewById(R.id.doctxtv);
		Txt_backtospeedname = (TextView) findViewById(R.id.bactxtv);
		Txt_feedratename = (TextView) findViewById(R.id.speedtxtv);

		Txt_millstype.setTypeface(type, Typeface.BOLD);
		Txt_subtype.setTypeface(type, Typeface.BOLD);
		Txt_diameter.setTypeface(type, Typeface.BOLD);
		Txt_hardness.setTypeface(type, Typeface.BOLD);
		Txt_shapeofcut.setTypeface(type, Typeface.BOLD);
        Txt_side.setTypeface(type, Typeface.BOLD);
        Txt_slot.setTypeface(type, Typeface.BOLD);

		Txt_workmaterialname.setTypeface(type, Typeface.BOLD);
		Txt_flutecountname.setTypeface(type, Typeface.BOLD);
		Txt_widthofcutname.setTypeface(type, Typeface.BOLD);
		Txt_depthofcutname.setTypeface(type, Typeface.BOLD);
		Txt_backtospeedname.setTypeface(type, Typeface.BOLD);
		Txt_feedratename.setTypeface(type, Typeface.BOLD);

		Txt_message = (TextView) findViewById(R.id.message);
		
/* set the font style  from asset folder */	
		subtypeViewGroup = findViewById(R.id.layout_feature);
		diameterViewGroup = findViewById(R.id.layout_diameter);
		widthofcutViewGroup = findViewById(R.id.layout_widthofcut);
		depthofViewGroup = findViewById(R.id.layout_depthofcut);
		workmaterialViewGroup = findViewById(R.id.layout_workmaterial);
		flutecountViewGroup = findViewById(R.id.layout_flutecount);
		hardnessViewGroup = findViewById(R.id.layout_hardness);
		shapeofcutViewGroup = findViewById(R.id.layout_shapeofcut);
		backtospeedViewGroup = findViewById(R.id.layout_backtospeed);
		feedrateViewGroup = findViewById(R.id.layout_feedrate);


		Txt_max1 = (TextView) findViewById(R.id.txtv1max);
		Txt_max2 = (TextView) findViewById(R.id.txtv2max);
		Txt_max3 = (TextView) findViewById(R.id.txtv3max);
		Txt_max4 = (TextView) findViewById(R.id.txtv4max);

		Txt_depthofcut = (TextView) findViewById(R.id.dipcuttext);
		Txt_backtospeed = (TextView) findViewById(R.id.revtext);
		Txt_feedrate = (TextView) findViewById(R.id.cutspdtext);
		Txt_widthofcut = (TextView) findViewById(R.id.widthofcut);

		Seekbar_withofcut = ((CustomSeekBar) findViewById(R.id.wcseekbar));
		Seekbar_depthofcut = ((CustomSeekBar) findViewById(R.id.dipcseekbar));
		Seekbar_backtospeed = (CustomSeekBar) findViewById(R.id.btsseekbar);
		Seekbar_feedrateseek = (CustomSeekBar) findViewById(R.id.frseekbar);

		Seekbar_depthofcut.setEnabled(true);
		Seekbar_withofcut.setEnabled(true);
		Seekbar_backtospeed.setEnabled(true);
		Seekbar_feedrateseek.setEnabled(true);

		Seekbar_withofcut.setProgressDrawable(null);
		Seekbar_depthofcut.setProgressDrawable(null);
		Seekbar_backtospeed.setProgressDrawable(null);
		Seekbar_feedrateseek.setProgressDrawable(null);

		Spinner_workmaterial = (Spinner) findViewById(R.id.spinnerid1);
		Spinner_flutecount = (Spinner) findViewById(R.id.spinnerid2);
		Spinner_diameter = (Spinner) findViewById(R.id.edia_meter);
		Spinner_hardness = (Spinner) findViewById(R.id.hardness_mills);
		Spinner_subtype = (Spinner) findViewById(R.id.sub_types_Spinner);
		Spinner_typeofmills = (Spinner) findViewById(R.id.mills_types_Spinner);

		Img_side = (ImageView) findViewById(R.id.rslid);
		Img_slot = (ImageView) findViewById(R.id.rslot);
	    Img_material = (ImageView) findViewById(R.id.imageView1);

		initDataToSeekbar();
/* set the array values for the spinners*/

		ArrayAdapter<CharSequence> typesofmills = ArrayAdapter
				.createFromResource(this, R.array.types_of_umills,
						android.R.layout.simple_spinner_item);

		typesofmills.setDropDownViewResource(R.layout.textsize);
		Spinner_typeofmills.setAdapter(typesofmills);

		Spinner_typeofmills.setAdapter(new NothingSelectedSpinnerAdapter(typesofmills,
				R.layout.contact_spinner_row_nothing_selected,

				this));

		final ArrayAdapter<String> millsmat = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, work_mills_material);

		final ArrayAdapter<String> millsmat1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, work_mills_material1);
		final ArrayAdapter<String> millsmat2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, work_mills_material2);
		final ArrayAdapter<String> millsmat3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, work_mills_material3);
		final ArrayAdapter<String> millsmat4 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, work_metrialen);

		final ArrayAdapter<String> millsdia1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mills_dia2);
		final ArrayAdapter<String> millsdia2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mills_dia8);
		final ArrayAdapter<String> millsdia3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mills_dia15);
		final ArrayAdapter<String> millsdia4 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mills_dia22);

		ArrayAdapter<String> sub_types = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, subtype);

		sub_types.setDropDownViewResource(R.layout.textsize);
		Spinner_subtype.setAdapter(sub_types);

		Spinner_subtype.setAdapter(new NothingSelectedSpinnerAdapter(sub_types,
				R.layout.contact_spinner_row_nothing_selected,

				this));

		final ArrayAdapter<String> sub_types1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, subtype1);

		final ArrayAdapter<String> sub_types2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, subtype2);

		// Hardness
		final ArrayAdapter<String> hardeness_adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, HRC);
		hardeness_adapter.setDropDownViewResource(R.layout.textsize);
		Spinner_hardness.setAdapter(hardeness_adapter);

		Spinner_hardness.setAdapter(new NothingSelectedSpinnerAdapter(
				hardeness_adapter,
				R.layout.contact_spinner_row_nothing_selected,

				this));

		// Spinner_flutecount

		ArrayAdapter<CharSequence> adapter_state2 = ArrayAdapter
				.createFromResource(this, R.array.uFluters_count,
						android.R.layout.simple_spinner_item);

		adapter_state2.setDropDownViewResource(R.layout.textsize);
		Spinner_flutecount.setAdapter(adapter_state2);

		Spinner_flutecount.setAdapter(new NothingSelectedSpinnerAdapter(
				adapter_state2, R.layout.contact_spinner_row_nothing_selected,

				this));

		
		
/* start the Workmaterial spinner selection event */		

		Spinner_workmaterial.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {

						S_workmaterialPos = String
								.valueOf(Spinner_workmaterial
										.getSelectedItemPosition());

						if (S_workmaterialPos.equalsIgnoreCase("0")) {
							Txt_shapeofcut.setVisibility(View.GONE);
							shapeofcutViewGroup.setVisibility(View.GONE);
                            Txt_side.setVisibility(View.GONE);
                            Txt_slot.setVisibility(View.GONE);
						} else {
							Txt_shapeofcut.setVisibility(View.VISIBLE);
							shapeofcutViewGroup.setVisibility(View.VISIBLE);
                            Txt_side.setVisibility(View.VISIBLE);
                            Txt_slot.setVisibility(View.VISIBLE);
						}

						passvalues();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});
		
/* start the hardness spinner selection event */	
		Spinner_hardness.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				
				/*S_hardnessPos = String.valueOf(Spinner_hardness	.getSelectedItemPosition());
				
				System.out.println("S_hardnessPos"+S_hardnessPos);

				if (S_typeofCal.equalsIgnoreCase("Drills")) 
				{
					

						S_imageOption="no";
						String ip_dia = String.valueOf(Spinner_diameter	.getSelectedItem());
						workmaterialViewGroup.setVisibility(View.GONE);
	
						if (S_hardnessPos.equalsIgnoreCase("0")) 
						{
							//workmaterialViewGroup.setVisibility(View.GONE);
	
						}
	
						else 
						{


								double caldia = Double.valueOf(ip_dia);
	
								if (ip_dia.equals("3.0"))
								{
									Fz = 0.04;
								} 
								else if (ip_dia.equals("4.0"))
								{
									Fz = 0.05;
								} 
								else if (ip_dia.equals("5.0"))
								{
									Fz = 0.06;
								}
								else if (ip_dia.equals("6.0"))
								{
									Fz = 0.07;
								}
	
								else if (ip_dia.equals("8.0"))
								{
									Fz = 0.08;
								}
								else if (ip_dia.equals("10.0")) 
								{
									Fz = 0.10;
								} 
								else if (ip_dia.equals("12.0"))
								{
									Fz = 0.12;
								} 
								else if (ip_dia.equals("16.0"))
								{
									Fz = 0.15;
								} 
								else if (ip_dia.equals("18.0"))
								{
									Fz = 0.18;
								}
								
								 drills calculation start here
								
								double pi = 3.1428571;
								Vc = 30;
								N = (Vc * 1000) / (pi * caldia);// back to speed
	
								System.out.println("backtospeed" + N);
	
								Vf = N * Fz;// feed rate
	
								System.out.println("feedrate" + Vf);
	
								Double_backtospeedMax = (int) N + ((int) N / 2);
								Double_feedrateMax = (int) Vf + ((int) Vf / 2);
	
								Seekbar_backtospeed.setMax((int) Double_backtospeedMax);
								Seekbar_feedrateseek.setMax((int) Double_feedrateMax);
	
								Seekbar_backtospeed.setProgress((int) N);// rev values
								Seekbar_feedrateseek.setProgress((int) Vf);// speed of cut
	
								String revval = String.valueOf(N);
	
								String cuttxtval = String.valueOf(Vf);
								Remove after . values(18.2355 to 18)
								if (revval.contains(".")) 
								{
									revval = revval.substring(0, revval.indexOf("."));
								}
								if (cuttxtval.contains(".")) 
								{
									cuttxtval = cuttxtval.substring(0,cuttxtval.indexOf("."));
	
								}

								Txt_backtospeed.setText(revval);// back to speed
								Txt_feedrate.setText(cuttxtval);// feedrate
	
								Seekbar_backtospeed.setEnabled(true);
								Seekbar_feedrateseek.setEnabled(true);
								
								shapeofcutViewGroup.setVisibility(View.GONE);
								workmaterialViewGroup.setVisibility(View.GONE);
								Img_material.setVisibility(View.VISIBLE);
								backtospeedViewGroup.setVisibility(View.VISIBLE);
								feedrateViewGroup.setVisibility(View.VISIBLE);
								widthofcutViewGroup.setVisibility(View.GONE);
								depthofViewGroup.setVisibility(View.GONE);

						}

				} 
				else 
				{
					
						if (S_hardnessPos.equalsIgnoreCase("0"))
						{
							
								workmaterialViewGroup.setVisibility(View.GONE);
						}
	
						else 
						{
							
							workmaterialViewGroup.setVisibility(View.VISIBLE);
									
						}
				}*/

		}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		
		/* start the Subtype  spinner selection event */	
		Spinner_subtype.setOnItemSelectedListener(new OnItemSelectedListener() {

			@SuppressWarnings("resource")
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				String pro_pos = S_millstypePos;
				S_subtypePos = String.valueOf(Spinner_subtype.getSelectedItemPosition());

				if (S_subtypePos.equalsIgnoreCase("0")) 
				{
					flutecountViewGroup.setVisibility(View.GONE);
					String toatl_valu = pro_pos;
					loadProductData(toatl_valu);
				} 
				else if (S_subtypePos.equalsIgnoreCase("1"))
				{
					flutecountViewGroup.setVisibility(View.VISIBLE);
					String toatl_valu = pro_pos;
					loadProductData(toatl_valu);

				}

				else if (S_subtypePos.equalsIgnoreCase("2"))
				{
					flutecountViewGroup.setVisibility(View.VISIBLE);
					String toatl_valu = pro_pos + ".1";

					System.out.println("su_type_Postionselval" + toatl_valu);

					loadProductData(toatl_valu);

				} else if (S_subtypePos.equalsIgnoreCase("3")) {
					flutecountViewGroup.setVisibility(View.VISIBLE);
					String toatl_valu = pro_pos + ".2";

					System.out.println("su_type_Postionselval" + toatl_valu);

					loadProductData(toatl_valu);

				}
				
				/*set image for  mills type     . get images from asset folder*/
				AssetManager assetManager = getAssets();
				InputStream istr;

				if (pro_pos.equalsIgnoreCase("2")) 
				{
						if (S_subtypePos.equalsIgnoreCase("1")) // select sub type
						{
								if (S_flutecountItem.equalsIgnoreCase("2")) // select flute count
								{
										try {
											istr = assetManager.open("images/" + "2a.png");
											Bitmap bitmap = BitmapFactory.decodeStream(istr);
											Img_material.setImageBitmap(bitmap);
											istr.close();
			
										}
										catch (IOException e)
										{
											e.printStackTrace();
										}
								}
								else 
								{
										try {
											istr = assetManager.open("images/" + "2b.png");
											Bitmap bitmap = BitmapFactory.decodeStream(istr);
											Img_material.setImageBitmap(bitmap);
											istr.close();
			
										}
										catch (IOException e) 
										{
											e.printStackTrace();
										}
								}
	
						} 
						else 
						{
							if (S_flutecountItem.equalsIgnoreCase("2")) 
							{
									try {
										istr = assetManager.open("images/" + "2c.png");
										Bitmap bitmap = BitmapFactory.decodeStream(istr);
										Img_material.setImageBitmap(bitmap);
										istr.close();
		
									} 
									catch (IOException e) 
									{
										e.printStackTrace();
									}
							} 
							else 
							{
									try {
										istr = assetManager.open("images/" + "2d.png");
										Bitmap bitmap = BitmapFactory.decodeStream(istr);
										Img_material.setImageBitmap(bitmap);
										istr.close();
		
									} 
									catch (IOException e)
									{
										e.printStackTrace();
									}
							}
						}
				}

				
				if (pro_pos.equalsIgnoreCase("3"))
				{
					if (S_subtypePos.equalsIgnoreCase("1"))
					{
						if (S_flutecountItem.equalsIgnoreCase("2"))
						{
								try {
									istr = assetManager.open("images/" + "3a.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						} 
						else
						{
								try {
									istr = assetManager.open("images/" + "3b.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						}

					} 
					else 
					{
						if (S_flutecountItem.equalsIgnoreCase("3"))
						{
								try {
									istr = assetManager.open("images/" + "3c.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						}
						else {
								try {
									istr = assetManager.open("images/" + "3d.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						}
					}
				}
				// sub type

				if (pro_pos.equalsIgnoreCase("24")) 
				{
					if (S_subtypePos.equalsIgnoreCase("1"))
					{
						if (S_flutecountItem.equalsIgnoreCase("2")) 
							{
								try {
									istr = assetManager.open("images/" + "24a.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						} 
						else if (S_flutecountItem.equalsIgnoreCase("3")) 
						{
								try {
									istr = assetManager.open("images/" + "24b.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						} 
						else if (S_flutecountItem.equalsIgnoreCase("4")) 
						{
								try {
									istr = assetManager.open("images/" + "24c.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						}

						else 
						{
								try {
									istr = assetManager.open("images/" + "24d.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						}
					} 
					else if (S_subtypePos.equalsIgnoreCase("2")) 
					{
						if (S_flutecountItem.equalsIgnoreCase("2")) 
						{
								try {
									istr = assetManager.open("images/" + "24la.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						} else if (S_flutecountItem.equalsIgnoreCase("4"))
						{
								try {
									istr = assetManager.open("images/" + "24lb.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						} else
						{
								try {
									istr = assetManager.open("images/" + "24lc.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						}

					} else 
					{
						if (S_flutecountItem.equalsIgnoreCase("2"))
							{
								try {
									istr = assetManager.open("images/" + "24fa.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						} else if (S_flutecountItem.equalsIgnoreCase("4"))
						{
								try {
									istr = assetManager	.open("images/" + "24fb.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						} else 
						{
								try {
									istr = assetManager.open("images/" + "24fc.png");
									Bitmap bitmap = BitmapFactory.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);
									istr.close();
	
								} catch (IOException e) {
									e.printStackTrace();
								}
						}

					}

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		/* start the diameter spinner selection event */	
		Spinner_diameter.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						S_diameterItem = String.valueOf(Spinner_diameter.getSelectedItem());// To get diameter values
						S_diameterPos = String.valueOf(Spinner_diameter.getSelectedItemPosition());//To get diameter postion
						System.out.println("diameter" + S_diameterItem);

						/*if (S_diameterPos.equalsIgnoreCase("0")) 
						{
							//hardnessViewGroup.setVisibility(View.GONE);
							
							workmaterialViewGroup.setVisibility(View.GONE);
						} else 
						{
							//hardnessViewGroup.setVisibility(View.VISIBLE);
							
							workmaterialViewGroup.setVisibility(View.VISIBLE);
						}
						*/
						
						
						// check the drills or mills type
						
						
						
						if (S_typeofCal.equalsIgnoreCase("Drills")) 
						{
							

								S_imageOption="no";
								String ip_dia = String.valueOf(Spinner_diameter	.getSelectedItem());
								workmaterialViewGroup.setVisibility(View.GONE);
			
								if (S_diameterPos.equalsIgnoreCase("0")) 
								{
									//workmaterialViewGroup.setVisibility(View.GONE);
			
								}
			
								else 
								{


										double caldia = Double.valueOf(ip_dia);
			
										if (ip_dia.equals("3.0"))
										{
											Fz = 0.04;
										} 
										else if (ip_dia.equals("4.0"))
										{
											Fz = 0.05;
										} 
										else if (ip_dia.equals("5.0"))
										{
											Fz = 0.06;
										}
										else if (ip_dia.equals("6.0"))
										{
											Fz = 0.07;
										}
			
										else if (ip_dia.equals("8.0"))
										{
											Fz = 0.08;
										}
										else if (ip_dia.equals("10.0")) 
										{
											Fz = 0.10;
										} 
										else if (ip_dia.equals("12.0"))
										{
											Fz = 0.12;
										} 
										else if (ip_dia.equals("16.0"))
										{
											Fz = 0.15;
										} 
										else if (ip_dia.equals("18.0"))
										{
											Fz = 0.18;
										}
										
										/* drills calculation start here*/
										
										double pi = 3.1428571;
										Vc = 30;
										N = (Vc * 1000) / (pi * caldia);// back to speed
			
										System.out.println("backtospeed" + N);
			
										Vf = N * Fz;// feed rate
			
										System.out.println("feedrate" + Vf);
			
										Double_backtospeedMax = (int) N + ((int) N / 2);
										Double_feedrateMax = (int) Vf + ((int) Vf / 2);
			
										Seekbar_backtospeed.setMax((int) Double_backtospeedMax);
										Seekbar_feedrateseek.setMax((int) Double_feedrateMax);
			
										Seekbar_backtospeed.setProgress((int) N);// rev values
										Seekbar_feedrateseek.setProgress((int) Vf);// speed of cut
			
										String revval = String.valueOf(N);
			
										String cuttxtval = String.valueOf(Vf);
			/*Remove after . values(18.2355 to 18)*/
										if (revval.contains(".")) 
										{
											revval = revval.substring(0, revval.indexOf("."));
										}
										if (cuttxtval.contains(".")) 
										{
											cuttxtval = cuttxtval.substring(0,cuttxtval.indexOf("."));
			
										}

										Txt_backtospeed.setText(revval);// back to speed
										Txt_feedrate.setText(cuttxtval);// feedrate
			
										Seekbar_backtospeed.setEnabled(true);
										Seekbar_feedrateseek.setEnabled(true);
										
										shapeofcutViewGroup.setVisibility(View.GONE);
										workmaterialViewGroup.setVisibility(View.GONE);
										Img_material.setVisibility(View.VISIBLE);
										backtospeedViewGroup.setVisibility(View.VISIBLE);
										feedrateViewGroup.setVisibility(View.VISIBLE);
										widthofcutViewGroup.setVisibility(View.GONE);
										depthofViewGroup.setVisibility(View.GONE);

								}

						} 
						else 
						{
							
								if (S_diameterPos.equalsIgnoreCase("0"))
								{
									
										workmaterialViewGroup.setVisibility(View.GONE);
								}
			
								else 
								{
									
									workmaterialViewGroup.setVisibility(View.VISIBLE);
											
								}
								
								passvalues();
						}

						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});

		/* start the type of mills spinner selection event */	
		Spinner_typeofmills.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Img_slot.setImageResource(R.drawable.blue_2);
				Img_side.setImageResource(R.drawable.blue_1);

				S_millstypePos = String.valueOf(Spinner_typeofmills	.getSelectedItemPosition());

				if (S_millstypePos.equalsIgnoreCase("4")
						|| S_millstypePos.equalsIgnoreCase("7")
						|| S_millstypePos.equalsIgnoreCase("11")
						|| S_millstypePos.equalsIgnoreCase("12")
						|| S_millstypePos.equalsIgnoreCase("13")
						|| S_millstypePos.equalsIgnoreCase("18")
						|| S_millstypePos.equalsIgnoreCase("21")) 
				{

					S_imageOption = "no";//To set image selection option
					S_typeofCal = "Drills";// To set Types of mills
					Img_slot.setImageResource(R.drawable.gray_slot);
					Img_side.setImageResource(R.drawable.grey_button_1);
					widthofcutViewGroup.setVisibility(View.GONE);
					depthofViewGroup.setVisibility(View.GONE);
					workmaterialViewGroup.setVisibility(View.GONE);
					flutecountViewGroup.setVisibility(View.GONE);
				} 
				else
				{
					S_imageOption = "yes";//To set image selection option
					S_typeofCal = "Mills";// To set Types of mills
					

				}

				if (S_millstypePos.equalsIgnoreCase("0")) {

					loadProductData(S_millstypePos);

					System.out.println(S_millstypePos + "S_millstypePos");

					subtypeViewGroup.setVisibility(View.GONE);
					widthofcutViewGroup.setVisibility(View.GONE);
					diameterViewGroup.setVisibility(View.GONE);
					depthofViewGroup.setVisibility(View.GONE);
					workmaterialViewGroup.setVisibility(View.GONE);
					flutecountViewGroup.setVisibility(View.GONE);
					hardnessViewGroup.setVisibility(View.GONE);
					shapeofcutViewGroup.setVisibility(View.GONE);
					backtospeedViewGroup.setVisibility(View.GONE);
					feedrateViewGroup.setVisibility(View.GONE);
					Txt_shapeofcut.setVisibility(View.GONE);
                    Txt_side.setVisibility(View.GONE);
                    Txt_slot.setVisibility(View.GONE);
                    Img_material.setVisibility(View.GONE);

				}
				else 
				{
					loadProductData(S_millstypePos);


					subtypeViewGroup.setVisibility(View.VISIBLE);

					Spinner_hardness.setAdapter(new NothingSelectedSpinnerAdapter(
									hardeness_adapter,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));
					
					widthofcutViewGroup.setVisibility(View.GONE);
					diameterViewGroup.setVisibility(View.GONE);
					depthofViewGroup.setVisibility(View.GONE);
					workmaterialViewGroup.setVisibility(View.GONE);
					flutecountViewGroup.setVisibility(View.GONE);
					hardnessViewGroup.setVisibility(View.GONE);
					shapeofcutViewGroup.setVisibility(View.GONE);
					backtospeedViewGroup.setVisibility(View.GONE);
					feedrateViewGroup.setVisibility(View.GONE);
					Txt_shapeofcut.setVisibility(View.GONE);
                    Txt_side.setVisibility(View.GONE);
                    Txt_slot.setVisibility(View.GONE);
					Img_material.setVisibility(View.GONE);

				}

				/* this used for table */

				if (S_millstypePos.equalsIgnoreCase("0")) {
					loadProductData(S_millstypePos);
					subtypeViewGroup.setVisibility(View.GONE);
					widthofcutViewGroup.setVisibility(View.GONE);
					diameterViewGroup.setVisibility(View.GONE);
					depthofViewGroup.setVisibility(View.GONE);
					workmaterialViewGroup.setVisibility(View.GONE);
					flutecountViewGroup.setVisibility(View.GONE);
					hardnessViewGroup.setVisibility(View.GONE);
					shapeofcutViewGroup.setVisibility(View.GONE);
					backtospeedViewGroup.setVisibility(View.GONE);
					feedrateViewGroup.setVisibility(View.GONE);
					Txt_shapeofcut.setVisibility(View.GONE);
                    Txt_side.setVisibility(View.GONE);
                    Txt_slot.setVisibility(View.GONE);
					Img_material.setVisibility(View.GONE);
					System.out.println("!!S_millstypePos" + S_millstypePos);
				}

				else if (S_millstypePos.equalsIgnoreCase("2")
						|| S_millstypePos.equalsIgnoreCase("3")
						|| S_millstypePos.equalsIgnoreCase("5")) {

					sub_types1.setDropDownViewResource(R.layout.textsize);
					Spinner_subtype.setAdapter(sub_types1);

					Spinner_subtype.setAdapter(new NothingSelectedSpinnerAdapter(
							sub_types1,
							R.layout.contact_spinner_row_nothing_selected,

							getApplicationContext()));

					subtypeViewGroup.setVisibility(View.VISIBLE);
					loadProductData(S_millstypePos);

					widthofcutViewGroup.setVisibility(View.GONE);
					diameterViewGroup.setVisibility(View.GONE);
					depthofViewGroup.setVisibility(View.GONE);
					workmaterialViewGroup.setVisibility(View.GONE);
					flutecountViewGroup.setVisibility(View.GONE);
					hardnessViewGroup.setVisibility(View.GONE);
					shapeofcutViewGroup.setVisibility(View.GONE);
					backtospeedViewGroup.setVisibility(View.GONE);
					feedrateViewGroup.setVisibility(View.GONE);
					Txt_shapeofcut.setVisibility(View.GONE);
                    Txt_side.setVisibility(View.GONE);
                    Txt_slot.setVisibility(View.GONE);
					Img_material.setVisibility(View.GONE);


				} 
				else if (S_millstypePos.equals("24")) 
				{
					sub_types2.setDropDownViewResource(R.layout.textsize);
					Spinner_subtype.setAdapter(sub_types2);

					Spinner_subtype.setAdapter(new NothingSelectedSpinnerAdapter(
							sub_types2,
							R.layout.contact_spinner_row_nothing_selected,

							getApplicationContext()));
					subtypeViewGroup.setVisibility(View.VISIBLE);
					widthofcutViewGroup.setVisibility(View.GONE);
					diameterViewGroup.setVisibility(View.GONE);
					depthofViewGroup.setVisibility(View.GONE);
					workmaterialViewGroup.setVisibility(View.GONE);
					flutecountViewGroup.setVisibility(View.GONE);
					hardnessViewGroup.setVisibility(View.GONE);
					shapeofcutViewGroup.setVisibility(View.GONE);
					backtospeedViewGroup.setVisibility(View.GONE);
					feedrateViewGroup.setVisibility(View.GONE);
					Txt_shapeofcut.setVisibility(View.GONE);
                    Txt_side.setVisibility(View.GONE);
                    Txt_slot.setVisibility(View.GONE);
					Img_material.setVisibility(View.GONE);
					loadProductData(S_millstypePos);


				} 
				else 
				{
					diameterViewGroup.setVisibility(View.GONE);
					subtypeViewGroup.setVisibility(View.GONE);
					widthofcutViewGroup.setVisibility(View.GONE);
					depthofViewGroup.setVisibility(View.GONE);
					workmaterialViewGroup.setVisibility(View.GONE);
					flutecountViewGroup.setVisibility(View.VISIBLE);
					hardnessViewGroup.setVisibility(View.GONE);
					shapeofcutViewGroup.setVisibility(View.GONE);
					backtospeedViewGroup.setVisibility(View.GONE);
					feedrateViewGroup.setVisibility(View.GONE);
					Txt_shapeofcut.setVisibility(View.GONE);
                    Txt_side.setVisibility(View.GONE);
                    Txt_slot.setVisibility(View.GONE);
					Img_material.setVisibility(View.GONE);
					loadProductData(S_millstypePos);


				}

				if (S_millstypePos.equalsIgnoreCase("2")) 
				{
					millsmat.setDropDownViewResource(R.layout.textsize);
					Spinner_workmaterial.setAdapter(millsmat);

					millsdia1
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					Spinner_diameter.setAdapter(millsdia1);

					Spinner_workmaterial
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsmat,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));

					Spinner_diameter
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsdia1,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));

				}
				else if (S_millstypePos.equalsIgnoreCase("8")) 
				{
					millsmat1.setDropDownViewResource(R.layout.textsize);
					Spinner_workmaterial.setAdapter(millsmat1);

					millsdia2
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					Spinner_workmaterial
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsmat1,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));

					Spinner_diameter.setAdapter(millsdia2);

					Spinner_diameter
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsdia2,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));
				}

				else if (S_millstypePos.equalsIgnoreCase("15")
						|| S_millstypePos.equalsIgnoreCase("16")
						|| S_millstypePos.equalsIgnoreCase("17")) 
				{
					millsmat2.setDropDownViewResource(R.layout.textsize);
					Spinner_workmaterial.setAdapter(millsmat2);

					Spinner_workmaterial
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsmat2,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));

					millsdia3
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					// attaching data adapter to spinner
					Spinner_diameter.setAdapter(millsdia3);

					Spinner_diameter
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsdia3,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));
				}

				else if (S_millstypePos.equalsIgnoreCase("22")) 
				{
					millsmat3.setDropDownViewResource(R.layout.textsize);
					Spinner_workmaterial.setAdapter(millsmat3);

					Spinner_workmaterial
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsmat3,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));
					millsdia4
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					// attaching data adapter to spinner
					Spinner_diameter.setAdapter(millsdia4);

					Spinner_diameter
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsdia4,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));

				}

				else 
				{
					millsmat4.setDropDownViewResource(R.layout.textsize);
					Spinner_workmaterial.setAdapter(millsmat4);

					Spinner_workmaterial
							.setAdapter(new NothingSelectedSpinnerAdapter(
									millsmat4,
									R.layout.contact_spinner_row_nothing_selected,

									getApplicationContext()));

				}

				Txt_depthofcut.setText("");
				Txt_backtospeed.setText("");
				Txt_feedrate.setText("");
				Txt_widthofcut.setText("");

				Seekbar_withofcut.setProgress(0);// depth of cut
				Seekbar_depthofcut.setProgress(0);// depth of cut
				Seekbar_backtospeed.setProgress(0);// rev values
				Seekbar_feedrateseek.setProgress(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
/* Start flute count spinner event */
		Spinner_flutecount
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						AssetManager assetManager = getAssets();
						InputStream istr;

						S_flutecountItem = String.valueOf(Spinner_flutecount.getSelectedItem());

						S_flutecountPos = String.valueOf(Spinner_flutecount	.getSelectedItemPosition());

						System.out.println("output" + S_flutecountItem);

						if (S_flutecountItem.equalsIgnoreCase("null")) 
						{
							Img_material.setVisibility(View.GONE);
							diameterViewGroup.setVisibility(View.GONE);
						} 
						else 
						{
							Img_material.setVisibility(View.VISIBLE);
							diameterViewGroup.setVisibility(View.VISIBLE);
						}
						if (S_millstypePos.equalsIgnoreCase("0"))
						{

						}
						else if (S_millstypePos.equalsIgnoreCase("1"))
						{

							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "1a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else
							{
									try {
										istr = assetManager.open("images/"
												+ "1b.png");
										Bitmap bitmap = BitmapFactory
												.decodeStream(istr);
										Img_material.setImageBitmap(bitmap);
	
										istr.close();
	
									} catch (IOException e) {
										e.printStackTrace();
									}
							}

						}
						else if (S_millstypePos.equalsIgnoreCase("2"))
						{

							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

									try {
										istr = assetManager.open("images/"
												+ "2a.png");
										Bitmap bitmap = BitmapFactory
												.decodeStream(istr);
										Img_material.setImageBitmap(bitmap);
	
										istr.close();
	
									} catch (IOException e) {
										e.printStackTrace();
									}
							}
							else
							{
								try {
									istr = assetManager.open("images/"
											+ "2b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}
						else if (S_millstypePos.equalsIgnoreCase("3")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "3a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else
							{
								try {
									istr = assetManager.open("images/"
											+ "3b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("4"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "4a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							

						} 
						else if (S_millstypePos.equalsIgnoreCase("5"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

									try {
										istr = assetManager.open("images/"
												+ "5a.png");
										Bitmap bitmap = BitmapFactory
												.decodeStream(istr);
										Img_material.setImageBitmap(bitmap);
	
										istr.close();
	
									} catch (IOException e) {
										e.printStackTrace();
									}
							}
							else
							{
								try {
									istr = assetManager.open("images/"
											+ "5b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("6"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "6a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else if (S_flutecountItem.equalsIgnoreCase("3"))
							{
								try {
									istr = assetManager.open("images/"
											+ "6a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else {

								try {
									istr = assetManager.open("images/"
											+ "6a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("7")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

									try {
										istr = assetManager.open("images/"
												+ "7a.png");
										Bitmap bitmap = BitmapFactory
												.decodeStream(istr);
										Img_material.setImageBitmap(bitmap);
	
										istr.close();
	
									} catch (IOException e) {
										e.printStackTrace();
									}
							}
							
						}

						else if (S_millstypePos.equalsIgnoreCase("8"))
						{

							if (S_flutecountItem.equalsIgnoreCase("3"))
							{

								try {
									istr = assetManager.open("images/"
											+ "8a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else 
							{
								try {
									istr = assetManager.open("images/"
											+ "8b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("9"))
						{

							if (S_flutecountItem.equalsIgnoreCase("3"))
							{

									try {
										istr = assetManager.open("images/"
												+ "9a.png");
										Bitmap bitmap = BitmapFactory
												.decodeStream(istr);
										Img_material.setImageBitmap(bitmap);
	
										istr.close();
	
									} catch (IOException e) {
										e.printStackTrace();
									}
							}
							else {
								try {
									istr = assetManager.open("images/"
											+ "9b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						} 
						else if (S_millstypePos.equalsIgnoreCase("10"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "10a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else 
							{
								try {
									istr = assetManager.open("images/"
											+ "10b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}

						else if (S_millstypePos.equalsIgnoreCase("11"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "11a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else {

							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("12"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "12a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							

						}

						else if (S_millstypePos.equalsIgnoreCase("13"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "13a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							
						} 
						else if (S_millstypePos.equalsIgnoreCase("14"))
						{
							if (S_flutecountItem.equalsIgnoreCase("4"))
							{

								try {
									istr = assetManager.open("images/"
											+ "14a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("15")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "15a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
						
							}

						}
						else if (S_millstypePos.equalsIgnoreCase("16"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "16a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							
							}

						}
						else if (S_millstypePos.equalsIgnoreCase("17"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "17a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}
						else if (S_millstypePos.equalsIgnoreCase("18"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "18a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
								
							}
							

						}
						else if (S_millstypePos.equalsIgnoreCase("19"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "19a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else 
							{
								try {
									istr = assetManager.open("images/"
											+ "19b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("20"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "20a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
						

						}

						} 
						else if (S_millstypePos.equalsIgnoreCase("21"))
						
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "21a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else {
								try {
									istr = assetManager.open("images/"
											+ "21b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}

						else if (S_millstypePos.equalsIgnoreCase("22"))
						{
							if (S_flutecountItem.equalsIgnoreCase("3"))
							{

								try {
									istr = assetManager.open("images/"
											+ "22a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else 
							{
								try {
									istr = assetManager.open("images/"
											+ "22b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}
						else if (S_millstypePos.equalsIgnoreCase("23")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("1"))
							{

								try {
									istr = assetManager.open("images/"
											+ "23a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						
						else if (S_millstypePos.equalsIgnoreCase("24")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("2")) 
							{

								try {
									istr = assetManager.open("images/"
											+ "24a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							} 
							else if (S_flutecountItem.equalsIgnoreCase("3"))
							{
							
								try {
									istr = assetManager.open("images/"
											+ "24b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							else if (S_flutecountItem.equalsIgnoreCase("4"))
							{
								try {
									istr = assetManager.open("images/"
											+ "24c.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							} 
							else 
							{
								try {
									istr = assetManager.open("images/"
											+ "24d.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						} 
						else if (S_millstypePos.equalsIgnoreCase("25"))
						{
							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "25a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
						
							}
						} 
						else if (S_millstypePos.equalsIgnoreCase("26")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("2")) 
							{

								try {
									istr = assetManager.open("images/"
											+ "26a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							} 
							else
							{
								try {
									istr = assetManager.open("images/"
											+ "26b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}

							}
						} 
						else if (S_millstypePos.equalsIgnoreCase("27"))
						{
							if (S_flutecountItem.equalsIgnoreCase("4"))
							{

								try {
									istr = assetManager.open("images/"
											+ "27a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else
							{
								try {
									istr = assetManager.open("images/"
											+ "27b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("28")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("4"))

							{
								try {
									istr = assetManager.open("images/"
											+ "28a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								try {
									istr = assetManager.open("images/"
											+ "28.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						else if (S_millstypePos.equalsIgnoreCase("29")) {
							if (S_flutecountItem.equalsIgnoreCase("4"))
							{

								try {
									istr = assetManager.open("images/"
											+ "29a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						
						} 
						
						else if (S_millstypePos.equalsIgnoreCase("30")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("6"))

							{
								try {
									istr = assetManager.open("images/"
											+ "30a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							} 
							else 
							{
								try {
									istr = assetManager.open("images/"
											+ "30b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("31"))
						{
							if (S_flutecountItem.equalsIgnoreCase("6"))

								try {
									istr = assetManager.open("images/"
											+ "31a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							else {

								try {
									istr = assetManager.open("images/"
											+ "31b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("32")) 
						{
							if (S_flutecountItem.equalsIgnoreCase("3"))
							{

								try {
									istr = assetManager.open("images/"
											+ "32a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else {
								try {
									istr = assetManager.open("images/"
											+ "32b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}

						else if (S_millstypePos.equalsIgnoreCase("33"))
						{
							if (S_flutecountItem.equalsIgnoreCase("4"))
							{

								try {
									istr = assetManager.open("images/"
											+ "33a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							
							}

						} 
						else if (S_millstypePos.equalsIgnoreCase("34")) 
						{

							if (S_flutecountItem.equalsIgnoreCase("2"))
							{

								try {
									istr = assetManager.open("images/"
											+ "34a.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
								
							}
							else 
							{
								try {
									istr = assetManager.open("images/"
											+ "34b.png");
									Bitmap bitmap = BitmapFactory
											.decodeStream(istr);
									Img_material.setImageBitmap(bitmap);

									istr.close();

								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}

						passvalues();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});

		
		/* working with seek bar change functions*/
		Seekbar_withofcut.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub

						I_widthofcutprogress = progress;

					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});

		Seekbar_depthofcut.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub
						I_depthofcutprogress = progress;
					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});

		Seekbar_backtospeed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						I_backtospeedprogress = progress;

						try {
							System.out.println("progressvalue"+ I_backtospeedprogress);

							int seek2barvalue = (int) Double_backtospeedMax	- (int) Double_backtospeedMax / 3;

							float seek2percentage = ((float) I_backtospeedprogress / (float) seek2barvalue) * 100;

							int seek3barvalue = (int) Double_feedrateMax- (int) Double_feedrateMax / 3;

							float seek3percentage = ((float) seek3barvalue / (float) 100)* seek2percentage;

							Seekbar_feedrateseek.setProgress((int) seek3percentage);

							String revval = String.valueOf(I_backtospeedprogress);
							String cuttxtval = String.valueOf(seek3percentage);

							if (revval.contains("."))
							{
								revval = revval.substring(0,revval.indexOf("."));
							}
							if (cuttxtval.contains("."))
							{
								cuttxtval = cuttxtval.substring(0,cuttxtval.indexOf("."));

							}

							Txt_backtospeed.setText(revval);
							Txt_feedrate.setText(cuttxtval);

							String msg1 = "Optimal operation ";
							Txt_message.setTextColor(Color
									.parseColor("#74DF00"));
							Txt_message.setText(msg1);
							Seekbar_backtospeed.setThumb(D_greenOffset);
							Seekbar_feedrateseek.setThumb(D_greenOffset);
							int grayzone = (int) seek2barvalue / 2;

							if (seek2barvalue < I_backtospeedprogress) 
							{

								String msg = "Dangerous mode";
								Txt_message.setTextColor(Color
										.parseColor("#FA5858"));
								Txt_message.setText(msg);
								Seekbar_backtospeed.setThumb(D_redOffset);
								Seekbar_feedrateseek.setThumb(D_redOffset);

							}

							if (I_backtospeedprogress < grayzone) 
							{

								Seekbar_backtospeed.setThumb(D_grayOffset);
								Seekbar_feedrateseek.setThumb(D_grayOffset);
								String msg = "Not effective mode";
								Txt_message.setTextColor(Color.parseColor("#6E6E6E"));
								Txt_message.setText(msg);

							}
						} catch (StackOverflowError e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					public void onStartTrackingTouch(SeekBar seekBar) {

						// TODO Auto-generated method stub
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});

		Seekbar_feedrateseek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

						try {
							I_feedrateprogress = progress;

							int seek3barvalue = (int) Double_feedrateMax- ((int) Double_feedrateMax / 3);

							float seek3percentage = ((float) I_feedrateprogress / (float) seek3barvalue) * 100;

							int seek2barvalue = (int) Double_backtospeedMax	- (int) Double_backtospeedMax / 3;

							float seek2percentage = ((float) seek2barvalue / (float) 100)* seek3percentage;

							Seekbar_backtospeed.setProgress((int) seek2percentage);

							String revval = String.valueOf(seek2percentage);

							String cuttxtval = String.valueOf(I_feedrateprogress);

							if (revval.contains("."))
							{
								revval = revval.substring(0,revval.indexOf("."));
							}
							if (cuttxtval.contains("."))
							{
								cuttxtval = cuttxtval.substring(0,cuttxtval.indexOf("."));

							}

							Txt_backtospeed.setText(revval);
							Txt_feedrate.setText(cuttxtval);
						} catch (StackOverflowError e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}

					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
					}
				});

		
		/* selet slot or side image seletion operations*/
		Img_slot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				S_shapeofcutId = "2";

				
				

				if (S_imageOption.equalsIgnoreCase("no"))
				{

				}

				else 
				{
					Img_material.setVisibility(View.VISIBLE);
					//widthofcutViewGroup.setVisibility(View.VISIBLE);
					depthofViewGroup.setVisibility(View.VISIBLE);
					backtospeedViewGroup.setVisibility(View.VISIBLE);
					feedrateViewGroup.setVisibility(View.VISIBLE);
					widthofcutViewGroup.setVisibility(View.GONE);
					Img_slot.setImageResource(R.drawable.blue_button_4);
					Img_side.setImageResource(R.drawable.blue_1);
					passvalues();

				}

			}
		});

		Img_side.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				S_shapeofcutId = "1";
				Img_material.setVisibility(View.VISIBLE);
				

				if (S_imageOption.equalsIgnoreCase("no")) 
				{

				}

				else {
					widthofcutViewGroup.setVisibility(View.VISIBLE);
					depthofViewGroup.setVisibility(View.VISIBLE);
					backtospeedViewGroup.setVisibility(View.VISIBLE);
					feedrateViewGroup.setVisibility(View.VISIBLE);
					Img_slot.setImageResource(R.drawable.blue_2);
					Img_side.setImageResource(R.drawable.blue_button_2);
					passvalues();

				}

			}

		});


	}

	private void initDataToSeekbar() {
		progressItemList = new ArrayList<ProgressItem>();

		// red span

		mProgressItem = new ProgressItem();
		mProgressItem.progressItemPercentage = ((F_greySpan / F_totalSpan) * 100);
		Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
		// mProgressItem.color = R.color.grey;
		progressItemList.add(mProgressItem);
		// blue span
		mProgressItem = new ProgressItem();
		mProgressItem.progressItemPercentage = (F_blueSpan / F_totalSpan) * 100;
		// mProgressItem.color = R.color.green;
		progressItemList.add(mProgressItem);
		// green span
		mProgressItem = new ProgressItem();
		mProgressItem.progressItemPercentage = (F_redSpan / F_totalSpan) * 100;
		// mProgressItem.color = R.color.red;
		progressItemList.add(mProgressItem);

		Seekbar_withofcut.initData(progressItemList);
		Seekbar_withofcut.invalidate();

		Seekbar_depthofcut.initData(progressItemList);
		Seekbar_depthofcut.invalidate();

		Seekbar_backtospeed.initData(progressItemList);
		Seekbar_backtospeed.invalidate();

		Seekbar_feedrateseek.initData(progressItemList);
		Seekbar_feedrateseek.invalidate();
	}

	private void loadProductData(String ProductNo) {
		// database handler
		// DatabaseHandler db = new DatabaseHandler(getApplicationContext());

		// Spinner Drop down elements
		double Typeofmills_pos = Double.valueOf(ProductNo);

		// product id

		if (Typeofmills_pos == 0)
		{
			Spinner_typeofmills.setSelection((int) Typeofmills_pos);

			System.out.println("1234" + Typeofmills_pos);
		}

		else 
		{

			System.out.println("1234" + Typeofmills_pos);

			List<String> diaP = dbHelper.getDiaP(ProductNo);
			List<String> flutecount = dbHelper.getFultecountP(ProductNo);
			ArrayAdapter<String> flutecountAdapter = new ArrayAdapter<String>(
					this, android.R.layout.simple_spinner_item, flutecount);
			flutecountAdapter.setDropDownViewResource(R.layout.textsize);
			Spinner_flutecount.setAdapter(flutecountAdapter);

			Spinner_flutecount.setAdapter(new NothingSelectedSpinnerAdapter(
					flutecountAdapter,
					R.layout.contact_spinner_row_nothing_selected,

					this));
			Spinner_typeofmills.setSelection((int) Typeofmills_pos);
			if (S_typeofCal.equals("Drills")) {

				final ArrayAdapter<String> drilldataAdapter = new ArrayAdapter<String>(
						this, android.R.layout.simple_spinner_item,
						type1_Diameter);

				drilldataAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				// attaching data adapter to spinner
				Spinner_diameter.setAdapter(drilldataAdapter);

				Spinner_diameter.setAdapter(new NothingSelectedSpinnerAdapter(
						drilldataAdapter,
						R.layout.contact_spinner_row_nothing_selected,

						this));

			} else 
			{

				if (S_millstypePos.equalsIgnoreCase("2")
						|| S_millstypePos.equalsIgnoreCase("8")
						|| S_millstypePos.equalsIgnoreCase("15")
						|| S_millstypePos.equalsIgnoreCase("16")
						|| S_millstypePos.equalsIgnoreCase("17")
						|| S_millstypePos.equalsIgnoreCase("22"))
				{

				}

				else {
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
							this, android.R.layout.simple_spinner_item, diaP);

					// Drop down layout style - list view with radio button
					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

					// attaching data adapter to spinner
					Spinner_diameter.setAdapter(dataAdapter);

					Spinner_diameter
							.setAdapter(new NothingSelectedSpinnerAdapter(
									dataAdapter,
									R.layout.contact_spinner_row_nothing_selected,

									this));

				}

			}
		}
	}

	// This method used for calculating mills  values 
	public void millscalculation() {
		String VcValue = null;
		System.out.println("S_workmaterialPos" + S_workmaterialPos);
		System.out.println("S_diameterItem" + S_diameterItem);
		int toothval = 0;

		S_flutecountItem = String.valueOf(Spinner_flutecount.getSelectedItem());
		if (S_millstypePos.equalsIgnoreCase("2")|| S_millstypePos.equalsIgnoreCase("8")) 
		{
			try {
				if (S_diameterPos.equalsIgnoreCase("0")	|| S_workmaterialPos.equalsIgnoreCase("0"))
				{

				} 
				else 
				{
					//Retrive values from database
					VcValue = dbHelper.getmillsformula(S_millstypePos,S_diameterItem, S_workmaterialPos);

					System.out.println("VcValue" + S_millstypePos + ","
							+ S_diameterItem + ","
							+ S_workmaterialPos);
				}

			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Txt_depthofcut.setText("");
				Txt_backtospeed.setText("");
				Txt_feedrate.setText("");
				Txt_widthofcut.setText("");

				Seekbar_withofcut.setProgress(0);// depth of cut
				Seekbar_depthofcut.setProgress(0);// depth of cut
				Seekbar_backtospeed.setProgress(0);// rev values
				Seekbar_feedrateseek.setProgress(0);
			}
		}

		else if (S_millstypePos.equalsIgnoreCase("15")
				|| S_millstypePos.equalsIgnoreCase("16")
				|| S_millstypePos.equalsIgnoreCase("17")) 
		{

			S_millstypePos = "15";

			try {

				if (S_diameterPos.equalsIgnoreCase("0")	|| S_workmaterialPos.equalsIgnoreCase("0")) 
				{

				} 
				else 
				{
					//Retrive values from database
					VcValue = dbHelper.getmillsformula(S_millstypePos,S_diameterItem, S_workmaterialPos);
				}

			} catch (IndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				Txt_depthofcut.setText("");
				Txt_backtospeed.setText("");
				Txt_feedrate.setText("");
				Txt_widthofcut.setText("");

				Seekbar_withofcut.setProgress(0);// depth of cut
				Seekbar_depthofcut.setProgress(0);// depth of cut
				Seekbar_backtospeed.setProgress(0);// rev values
				Seekbar_feedrateseek.setProgress(0);
			}
			System.out.println("S_millstypePos" + S_millstypePos);
		} 
		else if (S_millstypePos.equalsIgnoreCase("22"))

		{

			if (S_shapeofcutId != null) {
				try {

					if (S_diameterPos.equalsIgnoreCase("0")	|| S_workmaterialPos.equalsIgnoreCase("0")) {

					} else {
						//Retrive values from database

						VcValue = dbHelper.getmillsvalue(S_millstypePos,S_diameterItem, S_workmaterialPos,S_shapeofcutId);
					}
				} catch (IndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					Txt_depthofcut.setText("");
					Txt_backtospeed.setText("");
					Txt_feedrate.setText("");
					Txt_widthofcut.setText("");

					Seekbar_withofcut.setProgress(0);// depth of cut
					Seekbar_depthofcut.setProgress(0);// depth of cut
					Seekbar_backtospeed.setProgress(0);// rev values
					Seekbar_feedrateseek.setProgress(0);
				}
			}
			else
			{

			}
		} 
		else if (S_millstypePos.equalsIgnoreCase("24"))

		{

			if (S_shapeofcutId != null) {
				try {

					if (S_diameterPos.equalsIgnoreCase("0")	|| S_workmaterialPos.equalsIgnoreCase("0")) 
					{

					}
					else
					{

						VcValue = dbHelper.getmillsvalue(S_millstypePos,S_diameterItem, S_workmaterialPos,	S_shapeofcutId);
					}
				} catch (IndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					Txt_depthofcut.setText("");
					Txt_backtospeed.setText("");
					Txt_feedrate.setText("");
					Txt_widthofcut.setText("");

					Seekbar_withofcut.setProgress(0);// depth of cut
					Seekbar_depthofcut.setProgress(0);// depth of cut
					Seekbar_backtospeed.setProgress(0);// rev values
					Seekbar_feedrateseek.setProgress(0);
				}
			} 
			else {

			}
		} 
		
		else {

		}

		try {
			System.out.println("VcValue" + VcValue);

			StringTokenizer tokens = new StringTokenizer(VcValue, ",");
			String Vc = tokens.nextToken();// this will contain "Fruit"
			String Fz = tokens.nextToken();

			System.out.println("S_flutecountItem" + S_flutecountItem);
			System.out.println("Vc" + Vc);
			System.out.println("Fz" + Fz);

			if (S_flutecountPos.equalsIgnoreCase("0")) {
				toothval = 0;
			}

			
			// mills calculation part
			double Vcip = Double.valueOf(Vc);
			double Fzip = Double.valueOf(Fz);

			double diaip = Double.valueOf(S_diameterItem);

			toothval = Integer.valueOf(S_flutecountItem);

			double pi = 3.1428571;

			// Vc=30;
			N = (Vcip * 1000) / (pi * diaip);// back to speed
			Vf = N * Fzip * toothval;// feed rate


			Double_backtospeedMax = (int) N + ((int) N / 2);
			Double_feedrateMax = (int) Vf + ((int) Vf / 2);

			Seekbar_backtospeed.setMax((int) Double_backtospeedMax);
			Seekbar_feedrateseek.setMax((int) Double_feedrateMax);

			Seekbar_backtospeed.setProgress((int) N);// rev values
			Seekbar_feedrateseek.setProgress((int) Vf);// speed of cut
			String revval = String.valueOf(N);

			String cuttxtval = String.valueOf(Vf);

			if (revval.contains(".")) 
			{
				revval = revval.substring(0, revval.indexOf("."));
			}
			if (cuttxtval.contains("."))
			{
				cuttxtval = cuttxtval.substring(0, cuttxtval.indexOf("."));

			}

			Txt_backtospeed.setText(revval);// back to speed
			Txt_feedrate.setText(cuttxtval);// feedrate
		}
		catch (NullPointerException e)
		{
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			if (S_diameterPos.equalsIgnoreCase("0")) 
			{
				
			} 
			else 
			{

				if (S_shapeofcutId == null) 
				{

					Txt_backtospeed.setText("");// back to speed
					Txt_feedrate.setText("");// feed rate
					Txt_depthofcut.setText("");// depth of cut
					Txt_widthofcut.setText("");// with of cut

				} 
				
				//select shape of cut  calculation
				else if (S_shapeofcutId.equalsIgnoreCase("1")) 
				{
					sidecal();
				} 
				else 
				{
					slotcal();
				}
			}
		} 
		catch (NullPointerException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sidecal() {

		Double_inputdia = Double.parseDouble(S_diameterItem);

		if (Double_inputdia > 3)

		{

			Double_widthofcutIp = (Double_inputdia * 10) / 100;
			Double_depthofcutIp = (Double_inputdia * 150) / 100;// doc
			Double_widthofcutOp = Double_widthofcutIp * 10000;

			Double_depthofcutOp = Double_depthofcutIp * 10000;

			Duble_widthofcutMax = (Double_widthofcutOp * 150) / 100;
			Double_depthofcutMax = (Double_depthofcutOp * 150) / 100;// doc

			Seekbar_withofcut.setMax((int) Duble_widthofcutMax);
			Seekbar_depthofcut.setMax((int) Double_depthofcutMax);

			Txt_widthofcut.setText(String.valueOf(Double_widthofcutIp));

			Txt_depthofcut.setText(String.valueOf(Double_depthofcutIp));

			System.out.println("Double_widthofcutIp" + Double_widthofcutIp);
			System.out.println("Double_depthofcutIp" + Double_depthofcutIp);

			Seekbar_withofcut.setProgress((int) Double_widthofcutOp);// with of cut
			Seekbar_depthofcut.setProgress((int) Double_depthofcutOp);// depth of cut

			Seekbar_depthofcut.setThumb(D_greenOffset);
			Seekbar_withofcut.setThumb(D_greenOffset);

		} 
		else {

			System.out.println("doc" + Double_inputdia);

			Double_widthofcutIp = (Double_inputdia * 20) / 100;
			Double_depthofcutIp = (Double_inputdia * 150) / 100;// doc

			Double_depthofcutOp = Double_depthofcutIp * 10000;
			Double_widthofcutOp = Double_widthofcutIp * 10000;

			Duble_widthofcutMax = (Double_widthofcutOp * 150) / 100;
			Double_depthofcutMax = (Double_depthofcutOp * 150) / 100;// doc

			Seekbar_withofcut.setMax((int) Duble_widthofcutMax);
			Seekbar_depthofcut.setMax((int) Double_depthofcutMax);

			Txt_depthofcut.setText(String.valueOf(Double_depthofcutIp));
			Txt_widthofcut.setText(String.valueOf(Double_widthofcutIp));

			Seekbar_withofcut.setProgress((int) Double_widthofcutOp);// with of cut
			Seekbar_depthofcut.setProgress((int) Double_depthofcutOp);// depth of cut
			Seekbar_depthofcut.setThumb(D_greenOffset);
			Seekbar_withofcut.setThumb(D_greenOffset);

		}
	}

	public void slotcal() {

		Double_inputdia = Double.parseDouble(S_diameterItem);
		if (Double_inputdia < 2) 
		{

			Double_depthofcutIp = (Double_inputdia * 10) / 100;// dpth of cut seek bar
			Double_depthofcutOp = Double_depthofcutIp * 10000;
			Double_depthofcutMax = (Double_depthofcutOp * 150) / 100;// doc
			Seekbar_depthofcut.setMax((int) Double_depthofcutMax);
			Seekbar_depthofcut.setProgress((int) Double_depthofcutOp);// depth of cut
			Seekbar_depthofcut.setThumb(D_greenOffset);
			Txt_depthofcut.setText(String.valueOf(Double_depthofcutIp));


		} 
		else 
		{
			Double_depthofcutIp = (Double_inputdia * 20) / 100;// doc
			Double_depthofcutOp = Double_depthofcutIp * 10000;
			Double_depthofcutMax = (Double_depthofcutOp * 150) / 100;// doc
			Seekbar_depthofcut.setMax((int) Double_depthofcutMax);
			Seekbar_depthofcut.setProgress((int) Double_depthofcutOp);// depth of cut
			Seekbar_depthofcut.setThumb(D_greenOffset);
			
			Txt_depthofcut.setText(String.valueOf(Double_depthofcutIp));


		}
	}

	public void passvalues() {

		String dia_pos = S_diameterPos;

		String work_material = S_workmaterialPos;
		String flute_count = S_flutecountPos;
		System.out.println("S_subtypePos" + S_subtypePos);
		System.out.println("dia_pos" + dia_pos);
		System.out.println("work_material" + work_material);
		System.out.println("flute_count" + flute_count);
		if (dia_pos == null || work_material == null || flute_count == null) 
		{

			System.out.println("fails condition");

			Seekbar_backtospeed.setEnabled(false);
			Seekbar_feedrateseek.setEnabled(false);
			Seekbar_depthofcut.setEnabled(false);
			Seekbar_withofcut.setEnabled(false);

			Txt_depthofcut.setText("");
			Txt_backtospeed.setText("");
			Txt_feedrate.setText("");
			Txt_widthofcut.setText("");

			Seekbar_withofcut.setProgress(0);// depth of cut
			Seekbar_depthofcut.setProgress(0);// depth of cut
			Seekbar_backtospeed.setProgress(0);// rev values
			Seekbar_feedrateseek.setProgress(0);

			Seekbar_withofcut.setThumb(D_grayOffset);
			Seekbar_depthofcut.setThumb(D_grayOffset);
			Seekbar_backtospeed.setThumb(D_grayOffset);
			Seekbar_feedrateseek.setThumb(D_grayOffset);

		} 
		else if (dia_pos.equalsIgnoreCase("0")
				|| work_material.equalsIgnoreCase("0")
				|| flute_count.equalsIgnoreCase("0")) 
		{

			System.out.println("fails condition");

			Seekbar_backtospeed.setEnabled(false);
			Seekbar_feedrateseek.setEnabled(false);
			Seekbar_depthofcut.setEnabled(false);
			Seekbar_withofcut.setEnabled(false);

			Txt_depthofcut.setText("");
			Txt_backtospeed.setText("");
			Txt_feedrate.setText("");
			Txt_widthofcut.setText("");

			Seekbar_withofcut.setProgress(0);// depth of cut
			Seekbar_depthofcut.setProgress(0);// depth of cut
			Seekbar_backtospeed.setProgress(0);// rev values
			Seekbar_feedrateseek.setProgress(0);

			Seekbar_withofcut.setThumb(D_grayOffset);
			Seekbar_depthofcut.setThumb(D_grayOffset);
			Seekbar_backtospeed.setThumb(D_grayOffset);
			Seekbar_feedrateseek.setThumb(D_grayOffset);

		}

		else 
		{

			if (S_millstypePos.equalsIgnoreCase("2")
					|| S_millstypePos.equalsIgnoreCase("3")
					|| S_millstypePos.equalsIgnoreCase("8")
					|| S_millstypePos.equalsIgnoreCase("15")
					|| S_millstypePos.equalsIgnoreCase("16")
					|| S_millstypePos.equalsIgnoreCase("17")
					|| S_millstypePos.equalsIgnoreCase("22")
					|| S_millstypePos.equalsIgnoreCase("24"))

			{
				millscalculation();
				Seekbar_backtospeed.setEnabled(true);
				Seekbar_feedrateseek.setEnabled(true);
				Seekbar_depthofcut.setEnabled(false);
				Seekbar_withofcut.setEnabled(false);
			}

		}

	}

	
	//  show the language selection in menu bar
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.activity_main, menu);
	        return true;
	    }
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case R.id.item4:
	            {
	            	// change the text values to english

                    Txt_millstype.setText("Type of Mill");
                    Txt_diameter.setText("Diameter");
                    Txt_subtype.setText("Feature");
                    Txt_hardness.setText("Hardness");
                    Txt_shapeofcut.setText("Shape of Cut");
//                    Txt_shapeofcut.setText("Shape of Cut:     Side                                   Slot");
                    Txt_side.setText("Side");
                    Txt_slot.setText("Slot");
                    Txt_widthofcutname.setText("Width of Cut");
                    Txt_depthofcutname.setText("Depth of Cut");
                    Txt_backtospeedname.setText("Back to Speed");
                    Txt_feedratename.setText("Feed Rate");
                    Txt_workmaterialname.setText("Work Material");
                    Txt_flutecountname.setText("Flute Count");

	            }
	                Toast.makeText(this, "Language: English", Toast.LENGTH_SHORT).show();
	                return true;
	            case R.id.item5:
	            {

	            	// change the text values to english
	            	Txt_millstype.setText("Тип инструмента");
					Txt_diameter.setText("Диаметр");
					Txt_subtype.setText("Особенности");
					Txt_hardness.setText("твердость");
					Txt_shapeofcut.setText("Тип обработки");
//                    Txt_shapeofcut.setText("Тип обработки:  Уступ                               Паз");
                    Txt_side.setText("Уступ");
                    Txt_slot.setText("Паз");
                    Txt_hardness.setText("твердость");
                    Txt_widthofcutname.setText("Ширина реза");
					Txt_depthofcutname.setText("Глубина реза");
					Txt_backtospeedname.setText("Скорость шпинделя");
					Txt_feedratename.setText("Скорость подачи");
					Txt_workmaterialname.setText("Материал");
					Txt_flutecountname.setText("Кол-во режущих кромок");


	            }
	                Toast.makeText(this, "Language: Russian", Toast.LENGTH_SHORT).show();
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }

	    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit_press_back_twice_message, Toast.LENGTH_SHORT).show();
    }

    /*
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler;

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }  */

}
