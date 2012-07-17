package com.main;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends MapActivity implements LocationListener {

	private MapView mapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		LocationManager mlocManager = (LocationManager) getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		Criteria crit = new Criteria();
		// Queremos la mayor precision (GPS si esta disponible y prendido)
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		// Detectamos el mejor proveedor
		String provider = mlocManager.getBestProvider(crit, true);
		// Nos subscribimos a que nos avise cuando la ubicacion cambiar
		mlocManager.requestLocationUpdates(0, 0, crit, this, null);
		if (provider != null) {
			// Detectamos la ultima ubicacion del provider
			Location loc = mlocManager.getLastKnownLocation(provider);
			if (loc == null) {
				// Si el provider no nos puede dar la ubicacion preguntamos a
				// otro
				loc = mlocManager
						.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
				if (loc != null) {
					onLocationChanged(loc);
				}
			}
		}

		GeoPoint point = new GeoPoint(19240000, -99120000);
		addMarker(point, "Mexico", "Mexico");
	}

	@SuppressLint("ParserError")
	public void addMarker(GeoPoint point, String message1, String message2) {
		// Se obtienen las capas del mapa
		List<Overlay> mapOverlays = mapView.getOverlays();
		// Se obtiene un drawable que se mostrara en cada uno de los marcadores
		// que agregaremos a la capa
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.ic_launcher);
		// Se genera una capa
		MyLayer marker = new MyLayer(drawable, this);
		// Agrego un item con mensajes a mostrarse
		marker.addOverlay(new OverlayItem(point, message1, message2));
		mapOverlays.add(marker);
		mapView.getController().animateTo(point);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressLint("ParserError")
	public void addMarker(Location location, String message1, String message2) {
		// Se obtienen las capas del mapa
		List<Overlay> mapOverlays = mapView.getOverlays();
		// Se obtiene un drawable que se mostrara en cada uno de los marcadores
		// que agregaremos a la capa
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.ic_launcher);
		// Se genera una capa
		MyLayer marker = new MyLayer(drawable, this);
		// Genero una ubicacion en base a la location
		GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1e6),
				(int) (location.getLongitude() * 1e6)); // Agrego un item con
														// mensajes a mostrarse
		marker.addOverlay(new OverlayItem(point, message1, message2));
		mapOverlays.add(marker);
		mapView.getController().animateTo(point);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		addMarker(arg0, "Aca estoy yo", "Aca estoy!!");

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
