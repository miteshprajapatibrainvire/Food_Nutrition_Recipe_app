package com.example.food_nutrition_recipe_app.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.food_nutrition_recipe_app.R
import com.example.food_nutrition_recipe_app.adapter.TabAdapter
import com.example.food_nutrition_recipe_app.databinding.FragmentHomeBinding
import com.example.food_nutrition_recipe_app.model.BaseResponse
import com.example.food_nutrition_recipe_app.network.NetworkUtils
import com.example.food_nutrition_recipe_app.viewmodel.AddDietViewModel
import com.example.food_nutrition_recipe_app.viewmodel.DailyPlanViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var btnFloat:FloatingActionButton
    var spTouched: Boolean? = null
    var dataSession:String="sessionData"
     val viewModel by viewModels<AddDietViewModel>()
    lateinit var binding:FragmentHomeBinding
    lateinit var spString:String
//    private val args : HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentHomeBinding.inflate(inflater, container, false)
        tabLayout=binding.tabLayout
        viewPager=binding.viewPager
        btnFloat=binding.floatBtn

//        if(args.username != null) {
//            val name = args.username
//        Toast.makeText(requireContext(),"Hello "+ name.toString(), Toast.LENGTH_SHORT).show()
//        Log.e("=username=",name.toString())
//        }

        tabLayout.addTab(tabLayout.newTab().setText("Daily  Meal"))
        tabLayout.addTab(tabLayout.newTab().setText("Weekly Meal"))
        tabLayout.tabGravity= TabLayout.GRAVITY_FILL
        btnFloat.setOnClickListener {
            addDietDialogBox()
        }

//        if(NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.addDietResult.observe(requireActivity())
            {
                when (it) {
                    is BaseResponse.Success -> {
                        //                    Toast.makeText(requireContext(), "DiatType="+it.data?.dietType.toString()+"WeightGoal="+it.data?.weightGoal.toString()+"DietDuration="+
                        //                        it.data?.dietDuration.toString(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(
                            requireContext(),
                            "Meal Add Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        //                  resultDietResponse(it.data?.dietType.toString(), it.data?.weightGoal.toString(),
                        //                      it.data?.dietDuration.toString(), it.data?.dailyPlan?.get(0)!!.meals)
                        stopLoading()
                    }
                    is BaseResponse.Loading -> {
                        showLoading()
                    }
                    is BaseResponse.Error -> {
                        stopLoading()
                        Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                    }
                    else -> {

                    }
                }
            }
//        }
//        else
//        {
//            Toast.makeText(requireContext(), "Turn On Your Internet Connection!", Toast.LENGTH_SHORT).show()
//        }

        val adapter = TabAdapter(activity)
        adapter.addFragment(DailyMealFragment(), "DailyMeal")
        adapter.addFragment(WeeklyMealFragment(), "WeeklyMeal")

        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = 0
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()

//        val adapter= TabAdapter(requireActivity().supportFragmentManager,
//            tabLayout.tabCount)
//            viewPager.adapter=adapter
//            viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                viewPager.currentItem = tab.position
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//            }
//            override fun onTabReselected(tab: TabLayout.Tab) {
//            }
//        })

        binding.floatBtnBack.setOnClickListener {
            val session=context?.getSharedPreferences(dataSession, AppCompatActivity.MODE_PRIVATE)
            val edit=session?.edit()
            edit?.clear()
            edit?.apply()
            Toast.makeText(requireContext(), "Logout Successfully", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_homeFragment_to_mainFragment)
//            findNavController().popBackStack(R.id.action_homeFragment_to_mainFragment, true)
            findNavController().popBackStack()
        }

        return binding.root
    }

    fun showLoading() {
        binding.prgbar.visibility = View.VISIBLE
    }

    fun stopLoading() {
        binding.prgbar.visibility = View.GONE
    }

//    fun resultDietResponse(
//        txDietType:String,
//        txDietWeight:String,
//        txDietDuration:String,
//        mealS: List<MealXX>
//    )
//    {
//        val dialog=Dialog(requireContext())
//        dialog.setContentView(R.layout.result_diet_response)
//        val dietType=dialog.findViewById<MaterialTextView>(R.id.dietTypeid)
//        val dietWeight=dialog.findViewById<MaterialTextView>(R.id.dietWeightId)
//        val dietDuration=dialog.findViewById<MaterialTextView>(R.id.dietDurationiddata)
//        val recyclerView=dialog.findViewById<RecyclerView>(R.id.recyIdData)
//        dietType.text=txDietType
//        dietWeight.text=txDietWeight
//        dietDuration.text=txDietDuration.toString()
//        var adpDaily=DailyPlanAdapter(mealS)
//        recyclerView.adapter=adpDaily
//        recyclerView.layoutManager=LinearLayoutManager(requireContext())
//        dialog.show()
//        stopLoading()
//    }

    fun addDietDialogBox()
    {
        val dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.meal_plan_detail)
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val sp:Spinner=dialog.findViewById(R.id.spDiet)
        val slist = ArrayList<String>()
        val weightGoalEd=dialog.findViewById<EditText>(R.id.wightGoalId)
        val dietDurationEd=dialog.findViewById<EditText>(R.id.dietDurationId)
        val btnSaveDiet=dialog.findViewById<MaterialButton>(R.id.btnSaveDiet)
        slist.add("LOW_CARB")

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            slist
        )
        sp.adapter=adapter
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spTouched=true
                if (spTouched == true)
                {
                    spString= slist.get(p2)
                    spTouched=false
                }
            }
            override fun onNothingSelected(p: AdapterView<*>?) {
                Log.e("error=",p.toString())
            }
        }
        btnSaveDiet.setOnClickListener {
            viewModel.addDietPlan("LOW_CARB",Integer.parseInt(weightGoalEd.text.toString()),Integer.parseInt(dietDurationEd.text.toString()))
            dialog.cancel()
        }
        dialog.show()
    }

}