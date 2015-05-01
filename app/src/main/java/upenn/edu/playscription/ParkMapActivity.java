//package upenn.edu.playscription;
//
//import android.app.Activity;
//import android.os.Bundle;
//import com.google.android.gms.maps.*;
//import com.google.android.gms.maps.model.*;
//
//public class ParkMapActivity extends Activity implements OnMapReadyCallback {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map_layout);
//
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap map) {
//        LatLng currPos = new LatLng(-30.957, 140.218);
//
//        map.setMyLocationEnabled(true);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currPos, 13));
//
//        map.addMarker(new MarkerOptions()
//                .title("Current Position")
//                .position(currPos));
//    }
//}
