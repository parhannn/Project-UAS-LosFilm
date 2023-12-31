package com.example.pamuts.ui.home

import android.content.res.TypedArray
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pamuts.R
import kotlin.collections.ArrayList
import java.util.*

private const val t10 = "t1"
private const val t20 = "t2"

class HomeFragment : Fragment() {
    private var t1: String? = null
    private lateinit var adapter : MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var movieArrayList : ArrayList<MovieData>
    private lateinit var movieImage : TypedArray
    private lateinit var movieName: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            t1 = it.getString(t10)
            t1 = it.getString(t20)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        movieArrayList = arrayListOf<MovieData>()
        getUserData()
        adapter = MyAdapter(movieArrayList)
        recyclerView.adapter = adapter
        searchView = view.findViewById(R.id.search_action)
        adapter.onItemClick = {
            it.movieName?.let { it1 -> navigateToDetail(it1) }
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
        findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavMovieDetail(extraName))
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<MovieData>()
            for (i in movieArrayList) {
                if (i.movieName?.lowercase(Locale.ROOT)!!.contains(query)) {
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
        movieArrayList = arrayListOf<MovieData>()
        movieImage = resources.obtainTypedArray(R.array.movie_image_array)
        movieName = resources.getStringArray(R.array.movie_name_array)

        for (i in 0..<movieImage.length()) {
            val movie = MovieData(movieImage.getResourceId(i,0), movieName[i])
            movieArrayList.add(movie)
        }
    }

}