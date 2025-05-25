package denis.f108349.hospital.service;

import denis.f108349.hospital.data.model.Patient;
import reactor.core.publisher.Flux;

public interface ReportService {
//
//    /* ---------- dashboard counters ---------- */
//
//    /** # patients that have at least one visit with <diagnosisCode>. */
//    Mono<Long> countPatientsWithDiagnosis(String diagnosisCode);
//
//    /** # patients registered to each GP.  */
//    Flux<DoctorPatientCountDto> countPatientsPerGp();
//
//    /** # visits performed by each doctor. */
//    Flux<DoctorVisitCountDto> countVisitsPerDoctor();
//
//
//    /* ---------- (a) – (j) reports ---------- */
//
      Flux<Patient> getAllPatientsByDiagnosis(String diagnosisCode);
//
//    /** (c) patients whose GP is <gpId>. */
//    Flux<PatientDto> findPatientsByGp(UUID gpId);
//
//    /** (d) alias for {@link #countPatientsPerGp}. */
//    Flux<DoctorPatientCountDto> listPatientsPerGp();
//
//    /** (e) alias for {@link #countVisitsPerDoctor}. */
//    Flux<DoctorVisitCountDto> listVisitsPerDoctor();
//
//    /** (f) full visit list for a patient (➡ you **already** built this—omit if you like). */
//    Flux<VisitDto> findVisitsByPatient(UUID patientId);
//
//    /** (g) visits (any doctor) during [from, to]. */
//    Flux<VisitDto> findVisitsInPeriod(Instant from, Instant to);
//
//    /** (h) visits for <doctorId> during [from, to]. */
//    Flux<VisitDto> findVisitsByDoctorInPeriod(
//            UUID doctorId, Instant from, Instant to);
//
//    /** (i) month of the year with the most sick-leave certificates. */
//    Mono<MostIssuedMonthDto> findBusiestSickLeaveMonth();
//
//    /** (j) top-N doctors by issued sick leaves. */
//    Flux<DoctorSickLeaveCountDto> findTopDoctorsBySickLeaves(int topN);
}

