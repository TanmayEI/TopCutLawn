package com.topcutlawn.InvoiceListFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.databinding.FragmentInvoiceListBinding

class InvoiceListFragment : Fragment()  {

    private var _binding: FragmentInvoiceListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var invoiceAdapter: InvoiceAdapter
    private lateinit var invoicelist: List<InvoiceViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        invoicelist()

        binding.rvInvoicelist.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInvoicelist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        invoiceAdapter = InvoiceAdapter(invoicelist,requireContext())

        binding.rvInvoicelist.adapter = invoiceAdapter



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun invoicelist() {
        invoicelist = listOf(
            InvoiceViewModel(
                "10 march 2023 - 12:35pm",
            ),
            InvoiceViewModel(
                "10 march 2023 - 12:35pm",
            ),
            InvoiceViewModel(
                "10 march 2023 - 12:35pm",
            ),
        )
    }

}
