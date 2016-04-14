package main.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import main.exceptions.ServiceException;
import main.model.Appo;
import main.model.Auth;
import main.model.Dates;
import main.model.MedStaff;
import main.service.AppoSVC;
import main.service.DatesSVC;

public class PatientViewsBean {

	private AppoSVC appoSVC = new AppoSVC();
	private DatesSVC datesSVC = new DatesSVC();

	private List<Appo> pastAppsList = new ArrayList<Appo>();
	private List<Appo> nextAppsList = new ArrayList<Appo>();

	private Date selectedDate;
	private Long selectedDateId;
	private String selectedDateReason;
	private String selectedMedClass;

	private Dates newAppoDate = new Dates();

	private List<Dates> avaliableTimesList;
	
	private Appo appoDetail;

	private LoginBean loginBean;
	private Auth loggedUser = null;

	public void deleteAppo(Appo appo) {
		try {

			// TODO get param
			appoSVC.delete(appo);

		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error deleting appointment.");
		}

		ViewUtil.addMessage("Appointment canceled successfully ....");
	}

	public List<Appo> getPastAppsList() {

		
		try {
			pastAppsList = appoSVC.findAllPatientPastAppoByNhsNo(getLoggedUser().getNhsNo());
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error getting appointments list");
		}
		return pastAppsList;
	}

	public List<Appo> getNextAppsList() {
		
		try {
			nextAppsList = appoSVC.findAllPatientNextAppoByNhsNo(getLoggedUser().getNhsNo());
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error getting appointments list");
		}
		return nextAppsList;
	}

	public void onDateSelect(SelectEvent event) {
		
		if (selectedMedClass == null) {
			selectedDate = null;
			ViewUtil.addErrorMessage("Selected a target for your appointmet first");
			return;
		}

		Date selectedDate = (Date) event.getObject();
		if (selectedDate.before(new Date())) {
			avaliableTimesList = new ArrayList<Dates>();
			ViewUtil.addErrorMessage("Selected date is before today, choose another.");
			return;
		}
		try {
			avaliableTimesList = datesSVC.findAllDatesByDayAndMedClass(selectedDate, selectedMedClass);
		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error getting avaliable times");
		}

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		ViewUtil.addMessage("Date selected: " + format.format(selectedDate));
	}
	
	public void onMedClassSelect(AjaxBehaviorEvent event) {
			
			// Al cambiar el tipo de medstaff reseteamos los valores de fecha
			// y horas disponibles para volverlos a calcular
			selectedDate = null;
			avaliableTimesList = null;
	
		}

	public String saveAppo() {

		// Cogemos lo datos de la cita seleccionada
		newAppoDate.setDateId(selectedDateId);
		newAppoDate.setAppoDate(selectedDate);

		MedStaff candidate = findCandidateDocForDate(newAppoDate);

		// Si no hay candidato lanzamos un error
		if (candidate == null) {
			ViewUtil.addErrorMessage(
					"Appointment cannot be booked on " + newAppoDate.getAppoDate() + newAppoDate.getAppoTime());
			return "";
		}

		// Si hay candidato podemos crear la cita
		Appo appo = new Appo();
		appo.setDate(newAppoDate);
		appo.setMedStaff(candidate);
		appo.getPatient().setNhsNo(getLoggedUser().getNhsNo());
		appo.setReason(selectedDateReason);

		try {
			appoSVC.insert(appo);

		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error getting list of doc. for next appo.");
		}

		ViewUtil.addMessage("Appointment booked on " + newAppoDate.getAppoDate() + newAppoDate.getAppoTime());
		return "/patient/home";
	}

	public MedStaff findCandidateDocForDate(Dates appoDate) {

		// Ponemos el maximo valor porque cualquier cualquier numero de citas va
		// a ser menor que el maximo valor
		int lassApposCount = Integer.MAX_VALUE;
		int numAppos = 0;
		MedStaff candidateDoc = null;

		List<MedStaff> list = new ArrayList<>();
		try {
			// Obtinemos la lista de docs que estan disponibles para esa fecha y
			// hora (dateId)
			list = appoSVC.findAvailableMedStaffForDate(appoDate.getDateId(), selectedMedClass);
			for (MedStaff ms : list) {
				// PAra cada med de la lista obtenemos el numero de citas que
				// tiene en ese dia
				numAppos = appoSVC.getNoApposAtThisDay(ms.getMedId(), appoDate.getAppoDate());

				// Si el numero de citas es menor que el ultimo que
				// seleccionamos lo guardamos como candidato
				if (numAppos < lassApposCount) {
					lassApposCount = numAppos;
					candidateDoc = ms;
				}
			}

		} catch (ServiceException e) {
			e.printStackTrace();
			ViewUtil.addErrorMessage("Error getting list of doc. for next appo.");
		}

		// Devolvemos el candidato que sera el que menos citas tenga en ese dia
		return candidateDoc;
	}

	public void setPastAppsList(List<Appo> pastAppsList) {
		this.pastAppsList = pastAppsList;
	}

	public void setNextAppsList(List<Appo> nextAppsList) {
		this.nextAppsList = nextAppsList;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public List<Dates> getAvaliableTimesList() {
		return avaliableTimesList;
	}

	public void setAvaliableTimesList(List<Dates> avaliableTimesList) {
		this.avaliableTimesList = avaliableTimesList;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public Long getSelectedDateId() {
		return selectedDateId;
	}

	public void setSelectedDateId(Long selectedDateId) {
		this.selectedDateId = selectedDateId;
	}

	public Auth getLoggedUser() {
		if (loggedUser == null) {
			loggedUser = getLoginBean().getLoggedUser();
		}
		return loggedUser;
	}

	public String getSelectedDateReason() {
		return selectedDateReason;
	}

	public void setSelectedDateReason(String selectedDateReason) {
		this.selectedDateReason = selectedDateReason;
	}

	public String getSelectedMedClass() {
		return selectedMedClass;
	}

	public void setSelectedMedClass(String selectedMedClass) {
		this.selectedMedClass = selectedMedClass;
	}

	public Appo getAppoDetail() {
		if (appoDetail == null) {
			try {
				FacesContext fc = FacesContext.getCurrentInstance();
				Appo appo = new Appo();
				appo.getDate().setDateId(Long.parseLong(getDateIdParam(fc)));
				appo.getPatient().setNhsNo(getLoggedUser().getNhsNo());
				appo.getMedStaff().setMedId(Long.parseLong(getMedIdParam(fc)));
				appoDetail = appoSVC.findAppo(appo);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return appoDetail;
	}
	
	public String getDateIdParam(FacesContext fc) {

		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("dateId");

	}
	
	
	
	public String getMedIdParam(FacesContext fc) {

		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("medId");

	}

	public void setAppoDetail(Appo appoDetail) {
		this.appoDetail = appoDetail;
	}
	
	
}