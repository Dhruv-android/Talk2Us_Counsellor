package com.talk2us_Counsellor.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.iid.FirebaseInstanceId
import com.talk2us_Counsellor.R
import com.talk2us_Counsellor.models.Message
import com.talk2us_Counsellor.utils.Constants
import com.talk2us_Counsellor.utils.PrefManager
import com.talk2us_Counsellor.utils.Utils
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.absoluteValue

private const val MESSAGE_ID = "MessageId"

class ChatFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    lateinit var progress: ProgressBar
    private var messageId: String? = "not_defined"

    companion object {
        @JvmStatic
        fun newInstance(messageId:String) = ChatFragment().apply {
            arguments=Bundle().apply {
                putString(MESSAGE_ID,messageId)
            }
        }
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            messageId = it.getString(MESSAGE_ID)
        }
        val v = inflater.inflate(R.layout.chat_fragment, container, false)
        Utils.log(Utils.getCounsellorId())
        recyclerView = v.findViewById(R.id.rv_message_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val msg = v.findViewById<EditText>(R.id.et_message)
        progress = v.findViewById(R.id.progress)
        v.findViewById<Button>(R.id.bt_send).setOnClickListener {
            viewModel.sendMessage(
                Message(
                    msg.text.toString(),
                    Utils.getTime(),
                    false,
                    false,
                    Constants.COUNSELLOR,
                    viewModel.message_id
                )
            )
            msg.setText("")
        }
        msg.setOnClickListener {
            recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
        }
        val verticalScrollOffset = AtomicInteger(0)
        recyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            val y = oldBottom - bottom
            if (y.absoluteValue > 0) {
                // if y is positive the keyboard is up else it's down
                recyclerView.post {
                    if (y > 0 || verticalScrollOffset.get().absoluteValue >= y.absoluteValue) {
                        recyclerView.scrollBy(0, y)
                    } else {
                        recyclerView.scrollBy(0, verticalScrollOffset.get())
                    }
                }
            }
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(ChatViewModel::class.java)
        chatAdapter = ChatAdapter(requireContext())
        viewModel.message_id=messageId as String
        viewModel.getAllMessages(messageId as String).observe(requireActivity(), Observer {
            it?.let {
                chatAdapter.update(it)
                Utils.log(viewModel.message_id)
                recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
            }
        })
        viewModel.progress.observe(requireActivity(), Observer {
            if (it) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.INVISIBLE
            }
        })
        recyclerView.adapter = chatAdapter
        FirebaseDatabase.getInstance().getReference("chatMessages").child(
            viewModel.message_id
        ).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (message in p0.children) {
                    val messag = message.getValue(Message::class.java)
                    viewModel.insertLocally(messag as Message)
                }
            }

        })
    }

}
