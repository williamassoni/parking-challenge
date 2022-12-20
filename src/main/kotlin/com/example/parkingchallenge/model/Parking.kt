package com.example.parkingchallenge.model

import com.example.parkingchallenge.exceptions.NoAvailableSpot

/**
 * Representation of slot/space either from car or from the parking area
 */
class Slot(val size: Size, var occupied: Boolean) {
}

/**
 *  Representation of the parking area, it knows the space and also hold the status of the which one of the spots
 *
 *  @constructor slots --> amount of available slots in the parking area by default 1 SMALL 1 MEDIUM 1 LARGE
 */
class ParkingArea(slots: List<Slot> = arrayListOf(Slot(Size.SMALL, occupied = false), Slot(Size.MEDIUM, occupied = false), Slot(Size.LARGE, occupied = false))) {
    private var smallAreas : List<Slot> = slots.filter { it.size == Size.SMALL }
    private var mediumAreas : List<Slot> = slots.filter { it.size == Size.MEDIUM }
    private var largeAreas : List<Slot> = slots.filter { it.size == Size.LARGE }

    /**
     *  Determines weather of not there are available spots for determined size
     *
     * @param size
     */
    fun isAvailable(size: Size) : Boolean {
        return when(size) {
            Size.SMALL -> smallAreas.any { !it.occupied }
            Size.MEDIUM -> mediumAreas.any { !it.occupied }
            Size.LARGE -> largeAreas.any { !it.occupied }
        }
    }

    /**
     *  Mark a spot from the same size as 'occupied'
     *
     * @param size
     */
    fun markSpotAsOccupied(size: Size) {
        when(size) {
            Size.SMALL -> smallAreas.first { !it.occupied }.occupied = true
            Size.MEDIUM -> mediumAreas.first { !it.occupied }.occupied = true
            Size.LARGE -> largeAreas.first { !it.occupied }.occupied = true
        }
    }
}

/**
 *  Held the control of the ParingArea and is able to determine in which 'spot' the car should be parked
 *
 */
class ParkingAreaControl(private val parkingArea: ParkingArea=ParkingArea()) {

    /**
     *  Allocates a car inside the area(only when is possible either because the car a spot from the same size or lower available)
     *
     * @param car
     */
    fun allocate(car:Car) {
        val availableSize = determineAvailableSpotSizeForAllocation(car) ?: throw NoAvailableSpot()
        parkingArea.markSpotAsOccupied(availableSize)
    }

    /**
     *  Determine in which spot the car should be allocated:
     *      small cars --> can be parked at small/medium/large areas
     *      medium cars --> can be parked at medium/large areas
     *      large cars --> can be parked at large areas
     *
     * @param car
     */
    private fun determineAvailableSpotSizeForAllocation(car: Car) : Size? {
        return when {
            car.size == Size.SMALL && parkingArea.isAvailable(Size.SMALL) -> Size.SMALL
            car.size == Size.SMALL && parkingArea.isAvailable(Size.MEDIUM) -> Size.MEDIUM
            car.size == Size.SMALL && parkingArea.isAvailable(Size.LARGE) -> Size.LARGE
            car.size == Size.MEDIUM && parkingArea.isAvailable(Size.MEDIUM) -> Size.MEDIUM
            car.size == Size.MEDIUM && parkingArea.isAvailable(Size.LARGE) -> Size.LARGE
            car.size == Size.LARGE && parkingArea.isAvailable(Size.LARGE) -> Size.LARGE
            else -> return null
        }
    }

    /**
     *  Check if the parking space still have sport for determined size
     *
     * @param size to be checked
     */
    fun hasSpotAvailableFor(size: Size) : Boolean {
       return parkingArea.isAvailable(size)
    }
}