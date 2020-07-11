package maltepeuni.com.vehiclenetwork;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Delayed;

import maltepeuni.com.vehiclenetwork.database.Denounce;
import maltepeuni.com.vehiclenetwork.database.StandartUser;
import maltepeuni.com.vehiclenetwork.database.User;
import maltepeuni.com.vehiclenetwork.database.Vehicle;
import maltepeuni.com.vehiclenetwork.database.VehicleDetail;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {


	public static StandartUser user = new StandartUser();
	public static List<Vehicle> vehicles = new ArrayList<Vehicle>();
	public static List<Denounce> denounces = new ArrayList<Denounce>();


	public static MainFragment mainFragment;
	public static ProfileFragment profileFragment;
	public static SearchFragment searchFragment;
	public static DenounceFragment denounceFragment;
	public static OwnedCarsFragment ownedCarsFragment;
	public static AddCarFragment addCarFragment;
	public static EditCarFragment editCarFragment;
	public static CommentFragment commentFragment;
	public static CarListFragment carListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Change(searchFragment);
				setTitle("Vehicle Search Screen");
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		mainFragment = new MainFragment();
		profileFragment = new ProfileFragment();
		searchFragment = new SearchFragment();
		denounceFragment = new DenounceFragment();
		ownedCarsFragment = new OwnedCarsFragment();
		addCarFragment = new AddCarFragment();
		editCarFragment = new EditCarFragment();
		commentFragment = new CommentFragment();
		carListFragment = new CarListFragment();

        ServerCommunicationManager.M.mainActivity = this;
		Change(mainFragment);

	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();


		if (id == R.id.nav_main) {
			Change(mainFragment);
			setTitle(getString(R.string.app_name));
		} else if (id == R.id.nav_profile) {
			Change(profileFragment);
			setTitle("User Profile Screen");
		} else if (id == R.id.nav_vehicles) {
			Change(ownedCarsFragment);
			setTitle("Owned Vehicles");
		} else if (id == R.id.nav_search) {
			Change(searchFragment);
			setTitle("Vehicle Search Screen");
		} else if (id == R.id.nav_instructions) {
			Intent intent = new Intent(this, InstructionsActivity.class);
			startActivity(intent);
		} else if (id == R.id.nav_denounce) {
			Change(denounceFragment);
			setTitle("Denounce a Vehicle");
		}else if (id == R.id.nav_logout) {
			Intent intent = new Intent(this, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else /*if (id == R.id.nav_exit) */{
			System.exit(1);
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	public void Change(android.app.Fragment f ){
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_m, f);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	public static class MainFragment extends android.app.Fragment {
		public static MainActivity.MainFragment newInstance() {
			return new MainActivity.MainFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_main, container, false);
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class ProfileFragment extends android.app.Fragment {
		public static MainActivity.ProfileFragment newInstance() {
			return new MainActivity.ProfileFragment();
		}

		EditText e1;
		EditText e2;
		EditText e3;
		EditText e4;
		EditText e5;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_profile, container, false);

			e1 = (EditText)rootView.findViewById(R.id.editText2);
			e2 = (EditText)rootView.findViewById(R.id.editText3);
			e3 = (EditText)rootView.findViewById(R.id.editText4);
			e4 = (EditText)rootView.findViewById(R.id.editText5);
			e5 = (EditText)rootView.findViewById(R.id.editText6);

			e1.setText(user.name);
			e2.setText(user.email);
			e3.setText(user.passHash);
			e4.setText(user.phone);
			e5.setText(user.identity);

			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class OwnedCarsFragment extends android.app.Fragment {
		public static MainActivity.OwnedCarsFragment newInstance() {
			return new MainActivity.OwnedCarsFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_owned_cars, container, false);
			Button addCar = (Button)rootView.findViewById(R.id.buttonAddCar);
			addCar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_m, MainActivity.addCarFragment);
					transaction.addToBackStack(null);
					transaction.commit();
					getActivity().setTitle("Add a Vehicle");
				}
			});
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class DenounceFragment extends android.app.Fragment {
		public static MainActivity.DenounceFragment newInstance() {
			return new MainActivity.DenounceFragment();
		}
		int clicked = 0;
		ImageView iv1;
		ImageView iv2;
		ImageView iv3;
		ImageView iv4;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_denounce, container, false);

			iv1 = (ImageView)rootView.findViewById(R.id.imageView6);
			iv2 = (ImageView)rootView.findViewById(R.id.imageView7);
			iv3 = (ImageView)rootView.findViewById(R.id.imageView8);
			iv4 = (ImageView)rootView.findViewById(R.id.imageView9);

			iv1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					clicked = 0;
					openImageChooser();

				}
			});
			iv2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					clicked = 1;
					openImageChooser();

				}
			});
			iv4.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					clicked = 2;
					openImageChooser();
				}
			});
			iv3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					clicked = 3;
					openImageChooser();
				}
			});

			Button addCar = (Button)rootView.findViewById(R.id.buttonDenounceCar);
			addCar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Denounce d = new Denounce();

					ServerCommunicationManager.M.SendDenounce(d);

					Toast.makeText(getActivity(),"Your Denounce Information Sent Succefully.",Toast.LENGTH_LONG).show();

					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.fragment_m, MainActivity.mainFragment);
					transaction.addToBackStack(null);
					transaction.commit();
					getActivity().setTitle(getString(R.string.app_name));
				}
			});
			return rootView;
		}
		/* Choose an image from Gallery */
		void openImageChooser() {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
		}
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_OK) {
				if (requestCode == 100) {
					// Get the url from data
					Uri selectedImageUri = data.getData();
					if (null != selectedImageUri) {
						// Get the path from the Uri
						Log.i("IMAGE", "Image Path : " + selectedImageUri);
						// Set the image in ImageView
						switch (clicked){
							case 0: iv1.setImageURI(selectedImageUri); break;
							case 1: iv2.setImageURI(selectedImageUri); break;
							case 2: iv3.setImageURI(selectedImageUri); break;
							case 3: iv3.setImageURI(selectedImageUri); break;

						}
					}
				}
			}
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class SearchFragment extends android.app.Fragment {
		public static MainActivity.SearchFragment newInstance() {
			return new MainActivity.SearchFragment();
		}
		EditText ePlate;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_search, container, false);

			ePlate = (EditText)rootView.findViewById(R.id.editText16);
			Button addCar = (Button)rootView.findViewById(R.id.buttonSearchCar);
			addCar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if(ePlate.getText().toString().isEmpty()){
						ePlate.setError("Neccessary");
					}else{
						Vehicle v = new Vehicle();
						v.plateNumber = ePlate.getText().toString();

						//ServerCommunicationManager.M.Search(v,null);

						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.fragment_m, MainActivity.carListFragment);
						transaction.addToBackStack(null);
						transaction.commit();
						getActivity().setTitle(getString(R.string.app_name));
					}


				}
			});
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class AddCarFragment extends android.app.Fragment {
		public static MainActivity.AddCarFragment newInstance() {
			return new MainActivity.AddCarFragment();
		}

		int clicked = 0;
		ImageView iv1;
		ImageView iv2;
		ImageView iv3;
		EditText eChassis;
		EditText ePlate;
		EditText eColor;
		EditText eYear;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_add_vehicle, container, false);


			eChassis = (EditText)rootView.findViewById(R.id.editText7);
			ePlate = (EditText)rootView.findViewById(R.id.editText8);
			eColor = (EditText)rootView.findViewById(R.id.editText9);
			eYear = (EditText)rootView.findViewById(R.id.editText10);

			iv1 = (ImageView)rootView.findViewById(R.id.imageView5);
			iv2 = (ImageView)rootView.findViewById(R.id.imageView4);
			iv3 = (ImageView)rootView.findViewById(R.id.imageView3);

			Button addCar = (Button)rootView.findViewById(R.id.buttonUpdateCar);
			addCar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					boolean error = false;
					if(eChassis.getText().toString().isEmpty()){
						error = true;
						eChassis.setError("Neccessary");
					}
					if(ePlate.getText().toString().isEmpty()){
						error = true;
						ePlate.setError("Neccessary");
					}
					if(eColor.getText().toString().isEmpty()){
						error = true;
						eColor.setError("Neccessary");
					}
					if(eYear.getText().toString().isEmpty()){
						error = true;
						eYear.setError("Neccessary");
					}

					if(!error){
						Vehicle v = new Vehicle();
						v.plateNumber = ePlate.getText().toString();
						v.color = eColor.getText().toString();
						v.idUser = MainActivity.user.id;

						VehicleDetail vd = new VehicleDetail();

						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.fragment_m, MainActivity.ownedCarsFragment);
						transaction.addToBackStack(null);
						transaction.commit();
						getActivity().setTitle("Owned Vehicles");
					}
				}
			});

			iv1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					clicked = 0;
					openImageChooser();

				}
			});
			iv2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					clicked = 1;
					openImageChooser();

				}
			});
			iv3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					clicked = 2;
					openImageChooser();
				}
			});
			return rootView;
		}

		/* Choose an image from Gallery */
		void openImageChooser() {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
		}
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == RESULT_OK) {
				if (requestCode == 100) {
					// Get the url from data
					Uri selectedImageUri = data.getData();
					if (null != selectedImageUri) {
						// Get the path from the Uri
						Log.i("IMAGE", "Image Path : " + selectedImageUri);
						// Set the image in ImageView
						switch (clicked){
							case 0: iv1.setImageURI(selectedImageUri); break;
							case 1: iv2.setImageURI(selectedImageUri); break;
							case 2: iv3.setImageURI(selectedImageUri); break;

						}
					}
				}
			}
		}


	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class EditCarFragment extends android.app.Fragment {
		public static MainActivity.EditCarFragment newInstance() {
			return new MainActivity.EditCarFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_edit_car, container, false);
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class CommentFragment extends android.app.Fragment {
		public static MainActivity.CommentFragment newInstance() {
			return new MainActivity.CommentFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_comment, container, false);
			Button addCar = (Button)rootView.findViewById(R.id.buttonDenounceCar);
			addCar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					Denounce d = new Denounce();

					/*ServerCommunicationManager.M.SendDenounce(d);*/

					try {
						Thread.sleep(200);
					}catch(Exception e){

					}
					Toast.makeText(getActivity(),"Your Message Sended.",Toast.LENGTH_LONG).show();
				}
			});
			return rootView;
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	public static class CarListFragment extends android.app.Fragment {
		public static MainActivity.CarListFragment newInstance() {
			return new MainActivity.CarListFragment();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.content_car_list, container, false);
			return rootView;
		}
	}
	//-------------------------------------------------------------------------------------------------

	public void RefreshNews(){

	}

	public void SearchVehicleResult(){

	}

	public void ProfileResult(){

	}
}
