package com.example.jollycat.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.jollycat.data.model.Cat
import com.example.jollycat.R
import com.example.jollycat.data.model.Transaction
import com.example.jollycat.data.model.User
import java.text.NumberFormat
import java.util.Locale

object Constants {
    private var LIST_USERS: MutableList<User> = mutableListOf()
    var USER_POSITION: Int = -1
    private var LIST_TRANSACTION: MutableList<Transaction> = mutableListOf()
    var selectedID = -1

    fun getUsers(): MutableList<User> {
        return LIST_USERS
    }

    fun getNewUserID(): Int {
        Log.i("new user", LIST_USERS.size.toString())
        return LIST_USERS.size
    }

    fun setUsers(newUser: User) {
        Log.i("new user", newUser.getUserName())
        LIST_USERS.add(newUser)
    }

    fun getUserPosition(): Int {
        return USER_POSITION
    }

    fun setUserPosition(position: Int) {
        USER_POSITION = position
    }

    fun getSelectedUser(): User {

        for (user in LIST_USERS) {
            Log.i("this", USER_POSITION.toString())
            Log.i("that", user.getUserID().toString())
            if (user.getUserID() == USER_POSITION) {
                return user
            }
        }
        return User(-2, "", "", "")
    }


    fun getTransactions(): MutableList<Transaction> {
        val transactionList: MutableList<Transaction> = mutableListOf()
        for (list in LIST_TRANSACTION) {
            if (list.getBuyerID() == USER_POSITION) {
                transactionList.add(list)
            }
        }
        return transactionList
    }

    fun getNewTransactionID(): Double {
        return Math.random()
    }

    fun setTransaction(transaction: Transaction) {
        Log.i("new user", transaction.getCatName())
        LIST_TRANSACTION.add(transaction)
    }

    fun updateTransaction(quantity: Int, id: Double) {
        var position = 0;
        for (transaction in LIST_TRANSACTION) {
            if (id == transaction.getTransactionID()) {
                break;
            }
            position++;
        }
        LIST_TRANSACTION[position].setCatQuantity(quantity)
    }

    fun deleteTransaction(id: Double) {
        for ((index, list) in LIST_TRANSACTION.withIndex()) {
            if (list.getTransactionID() == id) {
                Log.i("index", index.toString())
                LIST_TRANSACTION.removeAt(index)
                return
            }
        }
    }

//    fun getCats() = listOf(
//        Cat(
//            CatID = 1,
//            CatName = "Persia",
//            CatDescription = "Kucing Persia dikenal dengan bulu panjangnya yang lebat dan wajah bulat yang manis. Mereka sering dianggap sebagai kucing yang anggun dan elegan. Meskipun memiliki temperamen yang tenang, mereka juga bisa menjadi sangat aktif saat bermain dengan mainan atau berinteraksi dengan pemiliknya.",
//            CatPrice = 350000,
//            CatImage = R.drawable.persia
//        ),
//        Cat(
//            CatID = 2,
//            CatName = "Siamese",
//            CatDescription = "Kucing Siamese adalah kucing yang elegan dengan tubuh yang ramping dan proporsi yang seimbang. Mereka memiliki sifat yang ramah dan suka berinteraksi dengan manusia. Selain itu, kecerdasan mereka membuat mereka mudah dilatih untuk trik-trik sederhana.",
//            CatPrice = 400000,
//            CatImage = R.drawable.siamese
//        ),
//        Cat(
//            CatID = 3,
//            CatName = "Maine Coon",
//            CatDescription = "Maine Coon adalah kucing yang kuat dan tangguh, namun juga lembut dan penyayang. Mereka memiliki sifat yang ramah terhadap manusia dan hewan peliharaan lainnya. Aktivitas fisik mereka yang tinggi membuat mereka cocok untuk pemilik yang aktif.",
//            CatPrice = 200000,
//            CatImage = R.drawable.maine_coon
//        ),
//        Cat(
//            CatID = 4,
//            CatName = "Scottish Fold",
//            CatDescription = "Kucing Scottish Fold memiliki penampilan yang unik dengan telinga yang dilipat ke depan. Mereka adalah kucing yang penuh kasih sayang dan cenderung menyesuaikan diri dengan berbagai lingkungan. Kehangatan dan kesabaran adalah sifat khas mereka.",
//            CatPrice = 700000,
//            CatImage = R.drawable.scottish_fold
//        ),
//        Cat(
//            CatID = 5,
//            CatName = "Bengal",
//            CatDescription = "Kucing Bengal memiliki kecantikan alami yang menarik dengan bulu yang bergaris-garis seperti macan tutul. Mereka adalah kucing yang penuh energi dan suka petualangan. Selain itu, mereka bisa menjadi teman yang sangat setia dan akrab dengan pemiliknya.",
//            CatPrice = 250000,
//            CatImage = R.drawable.bengal
//        ),
//        Cat(
//            CatID = 6,
//            CatName = "Sphynx",
//            CatDescription = "Kucing Sphynx adalah kucing yang penuh perhatian dan ramah, serta memiliki kepribadian yang unik. Meskipun mereka tidak memiliki bulu, mereka sering kali sangat suka bermain dan berinteraksi dengan pemiliknya. Mereka juga dikenal sebagai kucing yang paling cocok untuk alergi bulu.",
//            CatPrice = 950000,
//            CatImage = R.drawable.sphynx
//        ),
//        Cat(
//            CatID = 7,
//            CatName = "Ragdoll",
//            CatDescription = "Ragdoll adalah kucing yang sangat tenang dan santai, sehingga sering disebut sebagai \"boneka berbulu\". Mereka cenderung melunak ketika diangkat, sehingga cocok untuk keluarga dengan anak-anak kecil. Ragdoll sangat menyukai perhatian dan bisa menjadi teman yang setia.",
//            CatPrice = 500000,
//            CatImage = R.drawable.ragdoll
//        ),
//        Cat(
//            CatID = 8,
//            CatName = "Ragamuffin",
//            CatDescription = "Ragamuffin memiliki sifat yang lembut dan penyayang, serta suka bermain dan berinteraksi dengan manusia. Mereka adalah kucing yang hangat dan penuh kasih sayang, membuat mereka cocok untuk keluarga dengan anak-anak atau rumah dengan hewan peliharaan lainnya.",
//            CatPrice = 800000,
//            CatImage = R.drawable.ragamuffin
//        ),
//    )

    fun showSnackbar(activity: Activity, view: View, message: String) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show()
    }

    fun formatToRupiah(amount: Int): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(amount.toLong())
    }
}