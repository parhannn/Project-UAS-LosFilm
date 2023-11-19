package com.example.pamuts.ui.favorite

import android.content.res.TypedArray
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.collections.ArrayList
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pamuts.R
import androidx.appcompat.widget.SearchView
import java.util.*

private const val t10 = "t1"
private const val t20 = "t2"

class FavoriteFragment : Fragment() {
    private var t1: String? = null
    private lateinit var adapter: FavoriteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var favoriteArrayList: ArrayList<FavoriteData>
    private lateinit var favMovieImage : TypedArray
    private lateinit var favMovieName: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            t1 = it.getString(t10)
            t1 = it.getString(t20)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.fav_recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        favoriteArrayList = arrayListOf<FavoriteData>()
        getUserData()
        adapter = FavoriteAdapter(favoriteArrayList)
        recyclerView.adapter = adapter
        searchView = view.findViewById(R.id.fav_search_action)
        adapter.onItemClick = {
            it.favMovieName?.let { it1 -> navigateToDetail(it1) }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null)
                Handler(Looper.getMainLooper()).postDelayed({
                    filterList(newText)
                }, 1250)

                return false
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        t1 = null
    }

    private fun navigateToDetail(extraName: String){
        findNavController().navigate(FavoriteFragmentDirections.actionNavFavToNavMovieDetail(extraName))
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<FavoriteData>()
            for (i in favoriteArrayList) {
                if (i.favMovieName?.lowercase(Locale.ROOT)!!.contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
            }
            else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun getUserData() {
        favoriteArrayList = arrayListOf<FavoriteData>()
        favMovieImage = resources.obtainTypedArray(R.array.fav_movie_image_array)
        favMovieName = resources.getStringArray(R.array.fav_movie_name_array)

        for (i in 0..<favMovieImage.length()) {
            val favMovie = FavoriteData(favMovieImage.getResourceId(i,0), favMovieName[i])
            favoriteArrayList.add(favMovie)
        }
    }

}