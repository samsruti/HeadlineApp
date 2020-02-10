package com.samsruti.headlineapp.ui.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samsruti.headlineapp.DependencyInjection
import com.samsruti.headlineapp.R
import com.samsruti.headlineapp.databinding.HeadlinesFragmentBinding
import com.samsruti.headlineapp.model.Article
import com.samsruti.headlineapp.util.convertToDomainModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.headlines_fragment.*

class HeadlinesFragment : Fragment() {

//    private val headlinesViewModel by viewModel<HeadlinesViewModel>()

    private lateinit var headlinesViewModel: HeadlinesViewModel

    private var headlinesListAdapter: HeadlinesListAdapter? = null

    private lateinit var viewBinding: HeadlinesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = HeadlinesFragmentBinding.inflate(inflater,container,false)


        viewBinding.lifecycleOwner = this

        viewBinding.fab.setOnClickListener {
            Snackbar.make(viewBinding.container, "This FAB needs an action!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }




        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        headlinesViewModel = ViewModelProvider(this, DependencyInjection.provideViewModelFactory(viewBinding.root.context))
            .get(HeadlinesViewModel::class.java)

        viewBinding.viewModel = headlinesViewModel



        initAdapter(country = DEFAULT_COUNTRY)




        headlinesViewModel.navigateToSelectedNetworkNews.observe(this, Observer {
            if (it!=null){
                this.findNavController().navigate(HeadlinesFragmentDirections
                    .actionHeadlinesFragmentToDetailsFragment(convertToDomainModel(it)))
                headlinesViewModel.displayNewsDetailsComplete()
            }
        })

        viewBinding.swipeRefresh.setOnRefreshListener {
            initAdapter(country = "us")
        }
    }



    private fun initAdapter(country: String){

        viewBinding.swipeRefresh.isRefreshing = true



        headlinesListAdapter = HeadlinesListAdapter(HeadlinesListAdapter.CallBackClickListener{
            headlinesViewModel.displayNewsDetails(it)
        })

        viewBinding.headlinesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = headlinesListAdapter
        }

        headlinesViewModel.articles.observe(viewLifecycleOwner, Observer<PagedList<Article>> { headlines ->
            headlines?.apply {
                headlinesListAdapter?.submitList(headlines)
                viewBinding.swipeRefresh.isRefreshing = false
            }
        })

        headlinesViewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(viewBinding.root.context,"Error! $it",Toast.LENGTH_SHORT).show()
            viewBinding.swipeRefresh.isRefreshing = false
        })




        headlinesViewModel.fetchArticle(country)
    }


    companion object{
        private const val DEFAULT_COUNTRY = "in" //India
    }

}
