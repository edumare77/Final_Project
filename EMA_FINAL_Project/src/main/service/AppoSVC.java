package main.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.dao.AppoDAO;
import main.exceptions.DAOException;
import main.exceptions.ServiceException;
import main.model.Appo;
import main.model.MedStaff;
import main.model.User;

public class AppoSVC {
	private AppoDAO appoDAO;

	public AppoSVC() {
		appoDAO = new AppoDAO();
	}

	public List<Appo> findAll() throws ServiceException {

		List<Appo> list = new ArrayList<>();
		try {
			list = appoDAO.findAll();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}
	
	public List<Appo> findAllPatientPastAppoByNhsNo(Long nhsNo) throws ServiceException {

		List<Appo> list = new ArrayList<>();
		try {
			list = appoDAO.findAllPatientPastAppoByNhsNo(nhsNo);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}
	
	public List<Appo> findAllPatientRegAppoByMedId(Long medId) throws ServiceException {

		List<Appo> list = new ArrayList<Appo>();
		try {
			list = appoDAO.findAllPatientRegAppoByMedId(medId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}
	
	public List<Appo> findAllPatientNextAppoByNhsNo(Long nhsNo) throws ServiceException {

		List<Appo> list = new ArrayList<>();
		try {
			list = appoDAO.findAllPatientNextAppoByNhsNo(nhsNo);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}
	
	public List<Appo> findAllPatientNextAppoByMedId(Long medId) throws ServiceException {

		List<Appo> list = new ArrayList<>();
		try {
			list = appoDAO.findAllPatientNextAppoByMedId(medId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}

	public void delete(Appo appo) throws ServiceException {
		try {
			appoDAO.delete(appo);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void deleteByPatient(User user) throws ServiceException {
		try {
			appoDAO.deleteByPatient(user);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	public void deleteByMedStaff(MedStaff med) throws ServiceException {
		try {
			appoDAO.deleteByMedStaff(med);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean insert(Appo appo) throws ServiceException {
		try {
			if (appoDAO.insert(appo) == 1) {
				return true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return false;
	}
	
	public int getNoApposAtThisDay (Long medId, Date appoDate) throws ServiceException {

		int dc;
		
		try {
			dc = appoDAO.getNoApposAtThisDay(medId, appoDate);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return dc;
	}
	public List<MedStaff> findAvailableMedStaffForDate(Long dateId, String medClass) throws ServiceException {

		List<MedStaff> list = new ArrayList<>();
		
		try {
			list = appoDAO.findAvailableMedStaffForDate(dateId, medClass);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}

	public Appo findAppo(Appo appoIds) throws ServiceException {
		Appo appo;
		
		try {
			appo = appoDAO.findAppo(appoIds);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return appo;
	}

	public void updateAppo(Appo modifyAppo) throws ServiceException  {
		try {
			appoDAO.updateAppo(modifyAppo);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
}
