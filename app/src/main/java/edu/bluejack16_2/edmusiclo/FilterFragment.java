package edu.bluejack16_2.edmusiclo;


import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment implements View.OnClickListener{

    Drawable dArtist, dAlbum, dGenre, dFavorite;
    Button btnArtist, btnAlbum, btnGenre, btnFavorite;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_filter,container,false);

        dArtist = view.getResources().getDrawable(R.drawable.ic_artist);
        dAlbum = view.getResources().getDrawable(R.drawable.ic_album);
        dGenre = view.getResources().getDrawable(R.drawable.ic_genre);
        dFavorite = view.getResources().getDrawable(R.drawable.ic_favorite);

        btnArtist = (Button) view.findViewById(R.id.btnArtist);
        btnAlbum = (Button) view.findViewById(R.id.btnAlbum);
        btnGenre = (Button) view.findViewById(R.id.btnGenre);
        btnFavorite = (Button) view.findViewById(R.id.btnFavorite);

        dArtist.setBounds(0,0,120,120);
        dAlbum.setBounds(0,0,120,120);
        dGenre.setBounds(0,0,120,120);
        dFavorite.setBounds(0,0,120,120);

        btnArtist.setCompoundDrawables(null,dArtist,null,null);
        btnAlbum.setCompoundDrawables(null,dAlbum,null,null);
        btnGenre.setCompoundDrawables(null,dGenre,null,null);
        btnFavorite.setCompoundDrawables(null,dFavorite,null,null);

        Typeface varela = Typeface.createFromAsset(getActivity().getAssets(),"VarelaRound-Regular.ttf");

        btnArtist.setTypeface(varela);
        btnAlbum.setTypeface(varela);
        btnFavorite.setTypeface(varela);
        btnGenre.setTypeface(varela);

        btnArtist.setOnClickListener(this);
        btnAlbum.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);
        btnGenre.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == btnArtist){
            Intent intent = new Intent(getContext(), ArtistActivity.class);
            startActivity(intent);
        }else if(view == btnAlbum){
            Intent intent = new Intent(getContext(), AlbumActivity.class);
            startActivity(intent);
        }else if(view == btnFavorite){
            Intent intent = new Intent(getContext(), FavoriteActivity.class);
            startActivity(intent);
        }else if(view == btnGenre){
            Intent intent = new Intent(getContext(), GenreActivity.class);
            startActivity(intent);
        }
    }
}
