package com.sokolov.demo.utils;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;

/**
 * <p>1 Employee has 1 Timesheet for a week.</p>
 * <p>In this timesheet there are 5 WorkingDays</p>
 * <p>In a WorkingDay there are 9 DaySegments (09:00-10:00 ... 17:00-18:00)</p>
 * <table border="1">
 *   <tr>
 *     <td> 1. </td> <td> 09:00-10:00 </td>
 *   </tr>
 *   <tr>
 *     <td> 2. </td> <td> 10:00-11:00 </td>
 *   </tr>
 *   <tr>
 *     <td> 3. </td> <td> 11:00-12:00 </td>
 *   </tr>
 *   <tr>
 *     <td> 4. </td> <td> 12:00-13:00 </td>
 *   </tr>
 *   <tr>
 *     <td> 5. </td> <td> 13:00-14:00 </td>
 *   </tr>
 *   <tr>
 *     <td> 6. </td> <td> 14:00-15:00 </td>
 *   </tr>
 *   <tr>
 *     <td> 7. </td> <td> 15:00-16:00 </td>
 *   </tr>
 *   <tr>
 *     <td> 8. </td> <td> 16:00-17:00 </td>
 *   </tr>
 *   <tr>
 *     <td> 9. </td> <td> 17:00-18:00 </td>
 *   </tr>
 * </table>
 *
 * @author Sokolov_SA
 * @created 28.06.2021
 */

@Component
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FillingEmployeeScripts {

    public static final int EMPLOYEE_AMOUNT = 1000;
    public static final int TABLE_AMOUNT = EMPLOYEE_AMOUNT;
    public static final int WORKING_DAY_AMOUNT = TABLE_AMOUNT * 5;
    public static final int DAY_SEGMENT_AMOUNT = 9;

    private final EntityManagerFactory entityManagerFactory;

    /**
     * create employees with timesheets and working days
     */
    public void createEmployees() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Transaction transaction = session.getTransaction();

            transaction.begin();
            for (long i = 1; i <= EMPLOYEE_AMOUNT; i++) {

                session.createNativeQuery(
                        "INSERT INTO public.employee " +
                                "(name) " +
                                "VALUES (:name)"
                )
                        .setParameter("name", String.format("employee_%d", i))
                        .executeUpdate();

                session.createNativeQuery(
                        "INSERT INTO public.timesheet " +
                                "(employee_id, day_amount) " +
                                "VALUES (:employeeId, :dayAmount)"
                )
                        .setParameter("employeeId", i)
                        .setParameter("dayAmount", 5)
                        .executeUpdate();

                List<String> workingDays = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
                for (String workingDay : workingDays) {
                    session.createNativeQuery(
                            "INSERT INTO public.working_day " +
                                    "(timesheet_id, day_name) " +
                                    "VALUES (:timeSheetId, :dayName)"
                    ).setParameter("timeSheetId", i)
                            .setParameter("dayName", workingDay)
                            .executeUpdate();
                }
            }
            transaction.commit();
        }
    }

    public void fillTable_DaySegment() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Transaction transaction = session.getTransaction();

            transaction.begin();
            List<String> periods = Arrays.asList(
                    "09:00-10:00", "10:00-11:00", "11:00-12:00",
                    "12:00-13:00", "13:00-14:00", "14:00-15:00",
                    "15:00-16:00", "16:00-17:00", "17:00-18:00");
            for (String period : periods) {
                session.createSQLQuery(
                        "INSERT INTO public.day_segment " +
                                "(period) " +
                                "VALUES (:period)"
                ).setParameter("period", period)
                        .executeUpdate();
            }
            transaction.commit();
        }
    }

    /**
     * filling WorkingDay_DaySegment table script
     */
    public void fillTable_WorkingDay_DaySegment() {
        try (Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession()) {
            Transaction transaction = session.getTransaction();

            transaction.begin();
            for (int i = 1; i <= WORKING_DAY_AMOUNT; i++) {
                for (int j = 1; j <= DAY_SEGMENT_AMOUNT; j++) {
                    session.createSQLQuery(
                            "INSERT INTO public.working_day_to_day_segment " +
                                    "(working_day_id, day_segment_id) " +
                                    "VALUES (:workingDayId, :daySegmentId)"
                    ).setParameter("workingDayId", i)
                            .setParameter("daySegmentId", j)
                            .executeUpdate();
                }
            }
            transaction.commit();
        }
    }

}