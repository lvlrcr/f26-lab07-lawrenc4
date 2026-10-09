package edu.cmu.cs214.scheduling.workflow;

import edu.cmu.cs214.scheduling.domain.Booking;
import edu.cmu.cs214.scheduling.domain.BookingOutcome;
import edu.cmu.cs214.scheduling.domain.BookingRequest;
import edu.cmu.cs214.scheduling.domain.BookingStore;
import edu.cmu.cs214.scheduling.domain.Member;
import edu.cmu.cs214.scheduling.domain.Room;
import edu.cmu.cs214.scheduling.notify.NotificationHub;
import edu.cmu.cs214.scheduling.pricing.PriceCalculator;

/**
 * Everything the workflow does that depends on the booking type. One subclass
 * per {@link edu.cmu.cs214.scheduling.domain.BookingType}; the workflow does
 * the lookups shared by every type and hands the rest to the matching handler.
 */
abstract class BookingTypeHandler {

    static final String FACILITIES_CONTACT = "facilities@rooms.example.edu";

    protected final BookingStore store;
    protected final PriceCalculator calculator;
    protected final NotificationHub hub;

    BookingTypeHandler(BookingStore store, PriceCalculator calculator, NotificationHub hub) {
        this.store = store;
        this.calculator = calculator;
        this.hub = hub;
    }

    /** Validates and writes a request for a room that is known to exist. */
    abstract BookingOutcome submit(BookingRequest request, Room room);

    /** Releases a booking that exists and is not yet cancelled. */
    abstract boolean cancel(Booking booking, String roomName, boolean adminOverride);

    /** What the holder owes for a booking that exists. */
    abstract double priceOf(Booking booking);

    /** A one-line summary of a booking that exists. */
    abstract String describe(Booking booking, String roomName);

    static String recipientFor(Member member) {
        return member == null ? FACILITIES_CONTACT : member.getEmail();
    }
}
