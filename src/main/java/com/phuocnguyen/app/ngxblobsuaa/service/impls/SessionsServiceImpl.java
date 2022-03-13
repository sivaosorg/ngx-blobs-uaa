package com.phuocnguyen.app.ngxblobsuaa.service.impls;

import com.phuocnguyen.app.ngxblobssrv.model.filter.SessionsFilter;
import com.phuocnguyen.app.ngxblobssrv.model.filter.UsersFilter;
import com.phuocnguyen.app.ngxblobssrv.service.NgxUsersDetailsBaseService;
import com.phuocnguyen.app.ngxblobsuaa.entities.SessionsEntity;
import com.phuocnguyen.app.ngxblobsuaa.repositories.SessionsRepository;
import com.phuocnguyen.app.ngxblobsuaa.service.SessionsService;
import com.sivaos.Model.Request.PageAttributeOrderRequestDTO;
import com.sivaos.Model.Request.PageRequestDTO;
import com.sivaos.Model.Request.PdfRequestDTO;
import com.sivaos.Model.Response.SessionsResponseDTO;
import com.sivaos.Model.UserDTO;
import com.sivaos.Utility.CollectionsUtility;
import com.sivaos.Utils.LoggerUtils;
import com.sivaos.Utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SuppressWarnings({
        "FieldCanBeLocal",
        "DuplicatedCode"
})
@Service(value = "sessionsService")
@Transactional
public class SessionsServiceImpl implements SessionsService {

    private final static Logger logger = LoggerFactory.getLogger(SessionsServiceImpl.class);

    private final SessionsRepository sessionsRepository;
    private final NgxUsersDetailsBaseService ngxUsersDetailsBaseService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public SessionsServiceImpl(
            SessionsRepository sessionsRepository,
            NgxUsersDetailsBaseService ngxUsersDetailsBaseService) {
        this.sessionsRepository = sessionsRepository;
        this.ngxUsersDetailsBaseService = ngxUsersDetailsBaseService;
    }

    @Override
    public List<SessionsResponseDTO> findSessionsBy(SessionsFilter sessionsFilter) {
        return ngxUsersDetailsBaseService.findSessionsBy(sessionsFilter, entityManager);
    }

    @Override
    public void logoutSessions(UserDTO userDTO) {
        logger.info("SessionsService::logoutSessions::user = {}", userDTO);

        if (!ObjectUtils.allNotNull(userDTO)) {
            return;
        }

        UsersFilter usersFilter = new UsersFilter();
        usersFilter.setListId(Collections.singletonList(userDTO.getId()));
        usersFilter.setPageIndex(1);
        usersFilter.setPageSize(1);
        usersFilter.setViewParentsSessions(true);
        usersFilter.setSizeOfSessions(1);

        List<UserDTO> users = ngxUsersDetailsBaseService.findUsersBy(usersFilter, entityManager);

        if (CollectionsUtility.isEmpty(users)) {
            return;
        }

        List<SessionsResponseDTO> sessions = users.get(0).getSessions();

        if (CollectionsUtility.isEmpty(sessions)) {
            return;
        }

        SessionsResponseDTO session = sessions.get(0);
        logger.info("SessionsService::logoutSessions::session peek(0) = {}", LoggerUtils.toJson(session));
        SessionsEntity sessionsEntity = sessionsRepository.getOne(session.getId());
        sessionsEntity.setModifiedTime(new Date());
        sessionsEntity.setLastLogout(new Date());
        sessionsEntity.setLastLogoutMilliseconds(sessionsEntity.getLastLogout().getTime());
        sessionsRepository.save(sessionsEntity);
    }

    @Override
    public SessionsResponseDTO findOneById(Long aLong, Long aLong2) {
        return null;
    }

    @Override
    public SessionsResponseDTO saveAsPayloads(SessionsResponseDTO sessionsResponseDTO) {
        return null;
    }

    @Override
    public SessionsResponseDTO saveAsPayloads(SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void saves(SessionsResponseDTO sessionsResponseDTO) {

    }

    @Override
    public void saves(SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {

    }

    @Override
    public byte[] exportExcelUserEnabled(String s, Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserArchived(String s, Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserDisabled(String s, Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public ByteArrayInputStream exportCSVUserEnabled(Long aLong, List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserDisabled(Long aLong, List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserArchived(Long aLong, List<Long> list) {
        return null;
    }

    @Override
    public byte[] exportJsonUserEnabled(Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserDisabled(Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserArchived(Long aLong, List<Long> list) {
        return new byte[0];
    }

    @Override
    public List<SessionsResponseDTO> findAll() {
        return null;
    }

    @Override
    public List<SessionsResponseDTO> findAllByPayload(SessionsResponseDTO sessionsResponseDTO) {
        return null;
    }

    @Override
    public List<SessionsResponseDTO> findAllByPayload(SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public SessionsResponseDTO saveAsPayload(SessionsResponseDTO sessionsResponseDTO) {
        return null;
    }

    @Override
    public SessionsResponseDTO saveAsPayload(SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void save(SessionsResponseDTO sessionsResponseDTO) {

    }

    @Override
    public void save(SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {

    }

    @Override
    public SessionsResponseDTO updateAsPayload(SessionsResponseDTO sessionsResponseDTO) {
        return null;
    }

    @Override
    public SessionsResponseDTO updateAsPayload(SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void update(SessionsResponseDTO sessionsResponseDTO) {

    }

    @Override
    public void update(SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {

    }

    @Override
    public SessionsResponseDTO updateAsPayload(Long aLong, SessionsResponseDTO sessionsResponseDTO) {
        return null;
    }

    @Override
    public SessionsResponseDTO updateAsPayload(Long aLong, SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {
        return null;
    }

    @Override
    public void update(Long aLong, SessionsResponseDTO sessionsResponseDTO) {

    }

    @Override
    public void update(Long aLong, SessionsResponseDTO sessionsResponseDTO, UserDTO userDTO) {

    }

    @Override
    public SessionsResponseDTO findOneById(Long aLong) {
        return null;
    }

    @Override
    public SessionsResponseDTO findOneById(Long aLong, UserDTO userDTO) {
        return null;
    }

    @Override
    public List<SessionsResponseDTO> finAllByPreconditions(Long aLong) {
        return null;
    }

    @Override
    public void deleteOneById(Long aLong) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Long countAll() {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPage(int i, int i1) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(int i, int i1, String s, String s1) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(int i, int i1, Date date, Date date1, String s, String s1) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(int i, int i1, Date date, String s, String s1) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(int i, int i1, Date date, String s) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(int i, int i1, String s, String s1, String s2) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(int i, int i1, String s, String[] strings, String s1) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(int i, int i1, Object o, String[] strings) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(int i, int i1, String[] strings) {
        return null;
    }

    @Override
    public Page<List<SessionsResponseDTO>> findPages(int i, int i1) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(PageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findPages(PageRequestDTO pageRequestDTO, PageAttributeOrderRequestDTO pageAttributeOrderRequestDTO) {
        return null;
    }

    @Override
    public Boolean existsOneById(Long aLong) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findEnabled(int i, int i1, String s, String[] strings) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findDisabled(int i, int i1, String s, String[] strings) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findArchived(int i, int i1, String s, String[] strings) {
        return null;
    }

    @Override
    public byte[] exportExcelEnabled(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelDisabled(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelArchived(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserEnabled(String s, String s1, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserArchived(String s, String s1, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelUserDisabled(String s, String s1, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportExcelTemplate(String s) {
        return new byte[0];
    }

    @Override
    public ByteArrayInputStream exportCSVTemplate() {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVTemplateWith(List<SessionsResponseDTO> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVEnabled(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVDisabled(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVArchived(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserEnabled(String s, List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserDisabled(String s, List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportCSVUserArchived(String s, List<Long> list) {
        return null;
    }

    @Override
    public byte[] exportJsonTemplateWith(List<SessionsResponseDTO> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonEnabled(List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonDisabled(List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonArchived(List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserEnabled(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserDisabled(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public byte[] exportJsonUserArchived(String s, List<Long> list) {
        return new byte[0];
    }

    @Override
    public ByteArrayInputStream exportPdfTemplateWith(List<SessionsResponseDTO> list, PdfRequestDTO pdfRequestDTO) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportPdfEnabled(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportPdfDisabled(List<Long> list) {
        return null;
    }

    @Override
    public ByteArrayInputStream exportPdfArchived(List<Long> list) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findUserEnabled(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findUserDisabled(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findUserArchived(int i, int i1, String s, String s1, String[] strings) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findUserEnabled(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findUserDisabled(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }

    @Override
    public Page<SessionsResponseDTO> findUserArchived(int i, int i1, String s, UserDTO userDTO, String[] strings) {
        return null;
    }
}
