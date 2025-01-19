package com.theberdakh.fitness.domain.model

/**
 * Represents a subscription order with details about the associated subscription pack,
 * the total amount, and the current status of the order.
 *
 * I understand, difference between amount and pack's price is amount could mean how much user paid for subscription pack, and pack's price is the actual price of subscription pack.
 *
 * @property id The unique identifier of the subscription order.
 * @property pack The associated subscription pack for this order. Contains details about the pack such as its title and price.
 * @property amount The total amount for the subscription order, formatted as a string to include currency or special formats.
 * @property status The current status of the subscription order. Represented by [SubscriptionOrderStatus].
 * @property createdAt The timestamp indicating when the order was created, in the format "yyyy-MM-dd HH:mm:ss".
 */
data class SubscriptionOrder(
    val id: Int,
    val pack: SubscriptionPack,
    val amount: String,
    val status: SubscriptionOrderStatus,
    val paymentUrl: String,
    val createdAt: String
)




/**
 * Status of each training pack.
 * @property [SUBSCRIBED] user bought pack
 * @property [FINISHED] user completed pack
 * @property [NEW] user has not bought pack yet
 * @property [UNDEFINED] in case, status is mapping incorrectly */

enum class SubscriptionOrderStatus {
    SUBSCRIBED, FINISHED, NEW, UNDEFINED
}